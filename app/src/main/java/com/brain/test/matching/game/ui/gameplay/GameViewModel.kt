package com.brain.test.matching.game.ui.gameplay

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.brain.test.matching.game.ui.util.TimerState
import com.brain.test.matching.game.ui.util.TimerUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.StateFlow

class GameViewModel @AssistedInject constructor(
    @Assisted private val timer: Int,
) : ViewModel() {

    private val timerIntent = TimerUseCase(viewModelScope)
    val timerStateFlow: StateFlow<TimerState> = timerIntent.timerStateFlow

    init {
        timerIntent.toggleStartOrStop(timer * 60)
    }

    fun restartTimer() {
        timerIntent.toggleStartOrStop(timer * 60) // stop the timer
        timerIntent.toggleStartOrStop(timer * 60) // start the timer again
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