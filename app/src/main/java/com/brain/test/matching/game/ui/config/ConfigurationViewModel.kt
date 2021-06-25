package com.brain.test.matching.game.ui.config

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ConfigurationViewModel : ViewModel() {

    private val _timerStateFlow = MutableStateFlow(1)
    val timerStateFlow: StateFlow<Int> get() = _timerStateFlow

    fun increaseTimer() {
        _timerStateFlow.value = _timerStateFlow.value + 1
    }

    fun decreaseTimer() {
        if (_timerStateFlow.value > 1)
            _timerStateFlow.value = _timerStateFlow.value - 1
    }
}
