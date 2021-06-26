package com.brain.test.matching.game.ui.gameplay

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.brain.test.matching.game.R
import com.brain.test.matching.game.databinding.FragmentGameBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

/***
 * A fragment which presents a game play screen
 */
@AndroidEntryPoint
class GameFragment : Fragment(R.layout.fragment_game) {

    private val navArgs: GameFragmentArgs by navArgs()
    private val cardAdapter = GameAdapter {
        gameViewModel.onCardClicked(it)
    }

    @Inject
    lateinit var gameViewModelFactory: GameViewModel.AssistedFactory
    private val gameViewModel: GameViewModel by viewModels {
        GameViewModel.provideFactory(gameViewModelFactory, navArgs.timerValue)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentGameBinding.bind(view)
        binding.rvCards.adapter = cardAdapter
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            gameViewModel.timerStateFlow.collectLatest {
                binding.progressBar.max = it.totalSeconds
                binding.progressBar.progress = it.secondsRemaining ?: 0
                binding.tvTimeRemaining.text = it.displaySeconds.plus(" sec remaining")
            }
        }
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            gameViewModel.currentPointsStateFlow.collectLatest { points ->
                binding.tvTotalPoints.text = points.toString().plus(" points")
            }
        }
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            gameViewModel.cardsListStateFlow.collectLatest { cardsList ->
                cardAdapter.submitList(cardsList)
            }
        }
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            gameViewModel.isGameFinishedStateFlow.collect { isGameFinished ->
                if (isGameFinished) {
                    findNavController().navigate(
                        GameFragmentDirections.actionGameFragmentToResultFragment(
                            score = gameViewModel.currentPointsStateFlow.value,
                            timeLeft = gameViewModel.timerStateFlow.value.secondsRemaining ?: 0
                        )
                    )
                }
            }
        }
        binding.btnRestart.setOnClickListener {
            gameViewModel.restartGame()
        }
    }
}