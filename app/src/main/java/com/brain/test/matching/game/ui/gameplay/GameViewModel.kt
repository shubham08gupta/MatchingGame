package com.brain.test.matching.game.ui.gameplay

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

class GameViewModel @AssistedInject constructor(
    @Assisted private val timer: Int,
) : ViewModel() {


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