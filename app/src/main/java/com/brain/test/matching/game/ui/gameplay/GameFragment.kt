package com.brain.test.matching.game.ui.gameplay

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.brain.test.matching.game.R
import com.brain.test.matching.game.databinding.FragmentGameBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/***
 * A fragment which presents a game play screen
 */
@AndroidEntryPoint
class GameFragment : Fragment(R.layout.fragment_game) {

    private val navArgs: GameFragmentArgs by navArgs()

    @Inject
    lateinit var gameViewModelFactory: GameViewModel.AssistedFactory
    private val gameViewModel: GameViewModel by viewModels {
        GameViewModel.provideFactory(gameViewModelFactory, navArgs.timerValue)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentGameBinding.bind(view)
        binding.btnRestart.setOnClickListener {
            gameViewModel.restartTimer()
        }
    }
}