package com.brain.test.matching.game.ui.result

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.brain.test.matching.game.R

/***
 * A fragment to show the game result and provide an option to restart the game
 */
class ResultFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_result, container, false)
    }

}