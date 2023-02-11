package com.example.cardinfo.presentation

import androidx.recyclerview.widget.DiffUtil
import com.example.cardinfo.domain.entity.CardInfo

class CardInfoDiffCallback: DiffUtil.ItemCallback<CardInfo>() {
    override fun areItemsTheSame(oldItem: CardInfo, newItem: CardInfo): Boolean =
        oldItem.bin == newItem.bin

    override fun areContentsTheSame(oldItem: CardInfo, newItem: CardInfo): Boolean =
        oldItem == newItem
}