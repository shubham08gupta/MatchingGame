package com.brain.test.matching.game.ui.config

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.brain.test.matching.game.R
import com.brain.test.matching.game.databinding.FragmentConfigurationBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

/***
 * A fragment to do some initial configuration like selecting a timer
 */
@AndroidEntryPoint
class ConfigurationFragment : Fragment(R.layout.fragment_configuration) {

    private val viewModel: ConfigurationViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentConfigurationBinding.bind(view)
        binding.apply {
            btnMinus.setOnClickListener {
                viewModel.decreaseTimer()
            }
            btnPlus.setOnClickListener {
                viewModel.increaseTimer()
            }
            btnStart.setOnClickListener {
                findNavController().navigate(
                    ConfigurationFragmentDirections.actionConfigurationFragmentToGameFragment(
                        viewModel.timerStateFlow.value
                    )
                )
            }
        }
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.timerStateFlow.collectLatest { timerValue ->
                binding.tvTime.text = "$timerValue".plus(" min")
            }
        }
    }

}