package com.brain.test.matching.game.ui.result

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.brain.test.matching.game.R
import com.brain.test.matching.game.databinding.FragmentResultBinding

/***
 * A fragment to show the game result and provide an option to restart the game
 */
class ResultFragment : Fragment(R.layout.fragment_result) {

    private val navArgs: ResultFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentResultBinding.bind(view)
        binding.tvSummary.text =
            getString(R.string.message_results, navArgs.score, navArgs.timeLeft)
    }
}