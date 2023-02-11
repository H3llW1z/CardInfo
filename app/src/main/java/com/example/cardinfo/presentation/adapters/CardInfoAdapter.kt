package com.example.cardinfo.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.cardinfo.databinding.CardInfoItemBinding
import com.example.cardinfo.domain.entity.CardInfo
import com.example.cardinfo.presentation.CardInfoDiffCallback
import com.example.cardinfo.presentation.CardInfoViewHolder

class CardInfoAdapter: ListAdapter<CardInfo, CardInfoViewHolder>(CardInfoDiffCallback()) {

    var onItemClickListener: ((CardInfo) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardInfoViewHolder {
        val binding =
            CardInfoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CardInfoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CardInfoViewHolder, position: Int) {
        val item = currentList[position]
        with(holder.binding) {
            onItemClickListener?.let { listener ->
                root.setOnClickListener { listener(item)}
            }
            textViewBin.text = item.bin
        }
    }
}