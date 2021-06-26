package com.brain.test.matching.game.ui.gameplay

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.brain.test.matching.game.di.DefaultDispatcher
import com.brain.test.matching.game.ui.util.TimerState
import com.brain.test.matching.game.ui.util.TimerUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class GameViewModel @AssistedInject constructor(
    @Assisted private val timer: Int,
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _currentPointsStateFlow = MutableStateFlow(0)
    val currentPointsStateFlow: StateFlow<Int> get() = _currentPointsStateFlow

    private val timerIntent = TimerUseCase(viewModelScope)
    val timerStateFlow: StateFlow<TimerState> = timerIntent.timerStateFlow

    private val _cardsListStateFlow = MutableStateFlow<List<Card>>(emptyList())
    val cardsListStateFlow: StateFlow<List<Card>> get() = _cardsListStateFlow

    init {
        startGame()
    }

    private fun startGame() {
        timerIntent.toggleStartOrStop(timer * 60)
        _cardsListStateFlow.value = cardsList.shuffled()
        _currentPointsStateFlow.value = 0
    }

    fun restartGame() {
        timerIntent.toggleStartOrStop(timer * 60) // stop the timer
        startGame()
    }

    /***
     * Find the next [CardState] of the clicked [Card] by emitting a new list with state changes
     * of the clicked card. The other cards remains the same.
     */
    fun onCardClicked(card: Card) = viewModelScope.launch(dispatcher) {
        val list = _cardsListStateFlow.value.toMutableList()
        val clickedCardIndex = list.indexOfFirst { it.id == card.id }

        when (card.state) {
            CardState.Clickable.ImageHidden -> {

                // check if there is any other card shown already
                val otherShownCard = list.find { it.state == CardState.Clickable.ImageShown }
                if (otherShownCard == null) {
                    // show the clicked card
                    val newCard = card.copy(state = CardState.Clickable.ImageShown)
                    list[clickedCardIndex] = newCard
                    _cardsListStateFlow.value = list
                } else {
                    val otherShownCardIndex = list.indexOfFirst { it.id == otherShownCard.id }

                    // check if both the images match
                    _cardsListStateFlow.value = if (card.image == otherShownCard.image) {
                        // make both cards UnClickable because they matched
                        val clickedCard = card.copy(state = CardState.UnClickable)
                        val otherCard = otherShownCard.copy(state = CardState.UnClickable)
                        list[clickedCardIndex] = clickedCard
                        list[otherShownCardIndex] = otherCard

                        increasePoints()
                        list
                    } else {

                        // hide both the cards
                        val cardClicked = card.copy(state = CardState.Clickable.ImageHidden)
                        val otherCard = otherShownCard.copy(state = CardState.Clickable.ImageHidden)
                        list[clickedCardIndex] = cardClicked
                        list[otherShownCardIndex] = otherCard

                        decreasePoints()
                        list
                    }
                }

            }
            CardState.Clickable.ImageShown -> {

                val newCard = card.copy(state = CardState.Clickable.ImageHidden)
                list[clickedCardIndex] = newCard
                _cardsListStateFlow.value = list
            }
            CardState.UnClickable -> {
                // do nothing as this state cannot be reached since card is not clickable
            }
        }
    }

    private fun decreasePoints() {
        _currentPointsStateFlow.value = if (_currentPointsStateFlow.value - 2 < 0) {
            0
        } else {
            _currentPointsStateFlow.value - 2
        }
    }

    private fun increasePoints() {
        _currentPointsStateFlow.value = _currentPointsStateFlow.value + 5
    }


    /***
     * Use an assisted factory to inject manual parameters
     */
    @dagger.assisted.AssistedFactory
    interface AssistedFactory {
        fun create(
            @Assisted timer: Int,
        ): GameViewModel
    }

    companion object {
        fun provideFactory(
            assistedFactory: AssistedFactory,
            timer: Int,
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return assistedFactory.create(timer) as T
            }
        }
    }

}