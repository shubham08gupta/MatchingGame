package com.brain.test.matching.game.ui.gameplay

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.brain.test.matching.game.R
import com.brain.test.matching.game.databinding.LayoutItemCardBinding

class GameAdapter(
    private val onCardClicked: (Card) -> Unit,
) : ListAdapter<Card, GameAdapter.GameCardViewHolder>(DIFF_UTIL) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameCardViewHolder {
        val binding = LayoutItemCardBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return GameCardViewHolder(binding, onCardClicked)
    }

    override fun onBindViewHolder(holder: GameCardViewHolder, position: Int) {
        holder.bind(getItem(holder.bindingAdapterPosition))
    }

    inner class GameCardViewHolder(
        private val binding: LayoutItemCardBinding,
        private val onCardClicked: (Card) -> Unit,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(card: Card) {
            binding.apply {
                when (card.state) {
                    CardState.Clickable.ImageHidden -> {
                        with(imageView) {
                            isClickable = true
                            setBackgroundResource(R.drawable.drawable_placeholder)
                            setOnClickListener {
                                onCardClicked(card)
                            }
                        }
                    }
                    CardState.Clickable.ImageShown -> {
                        with(imageView) {
                            isClickable = true
                            setBackgroundResource(card.image)
                            setOnClickListener {
                                onCardClicked(card)
                            }
                        }
                    }
                    CardState.UnClickable -> {
                        with(imageView) {
                            isClickable = false
                            setBackgroundResource(card.image)
                        }
                    }
                }
            }
        }
    }

    companion object {
        private val DIFF_UTIL = object : DiffUtil.ItemCallback<Card>() {
            override fun areItemsTheSame(oldItem: Card, newItem: Card): Boolean =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: Card, newItem: Card): Boolean =
                oldItem == newItem

        }
    }

}
