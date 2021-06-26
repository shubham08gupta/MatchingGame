package com.brain.test.matching.game.ui.gameplay

import androidx.annotation.DrawableRes
import com.brain.test.matching.game.R

data class Card(
    val id: Int, // unique identifier used to search a particular card
    @DrawableRes val image: Int, // the image of the card
    val state: CardState = CardState.Clickable.ImageHidden // all cards are hidden initially
)

sealed class CardState {
    object UnClickable : CardState() // state when the cards are matched(a.k.a.success state)
    sealed class Clickable : CardState() {
        object ImageShown : Clickable() // clickable and image shown
        object ImageHidden : Clickable() // clickable but image hidden
    }
}

/**
 * Creates a list of 8 pairs(16 items) with same [Card.image]
 */
val cardsList = listOf(
    Card(1, R.drawable.drawable_animal_nezumi),
    Card(9, R.drawable.drawable_animal_nezumi),

    Card(2, R.drawable.drawable_animal_tora),
    Card(10, R.drawable.drawable_animal_tora),

    Card(3, R.drawable.drawable_animal_tatsu),
    Card(11, R.drawable.drawable_animal_tatsu),

    Card(4, R.drawable.drawable_animal_hebi),
    Card(12, R.drawable.drawable_animal_hebi),

    Card(5, R.drawable.drawable_animal_hitsuji),
    Card(13, R.drawable.drawable_animal_hitsuji),

    Card(6, R.drawable.drawable_animal_saru),
    Card(14, R.drawable.drawable_animal_saru),

    Card(7, R.drawable.drawable_animal_inu),
    Card(15, R.drawable.drawable_animal_inu),

    Card(8, R.drawable.drawable_animal_inoshishi),
    Card(16, R.drawable.drawable_animal_inoshishi),
)