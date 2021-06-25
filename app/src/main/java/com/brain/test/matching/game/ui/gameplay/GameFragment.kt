package com.brain.test.matching.game.ui.gameplay

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.brain.test.matching.game.R
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/***
 * A fragment which presents a game play screen
 */
@AndroidEntryPoint
class GameFragment : Fragment() {

    private val navArgs: GameFragmentArgs by navArgs()

    @Inject
    lateinit var gameViewModelFactory: GameViewModel.AssistedFactory
    private val gameViewModel: GameViewModel by viewModels {
        GameViewModel.provideFactory(gameViewModelFactory, navArgs.timerValue)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_game, container, false)
    }
}