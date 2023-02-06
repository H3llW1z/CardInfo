package com.example.cardinfo.domain.repository

import androidx.lifecycle.LiveData
import com.example.cardinfo.domain.entity.CardInfo
import com.example.cardinfo.domain.entity.Result

interface CardInfoRepository {

    fun getCardInfo(bin: String): Result

    fun getPreviousRequests(): LiveData<List<CardInfo>>

    fun addToPreviousRequests(cardInfo: CardInfo)

    fun removeFromPreviousRequests(id: Int)

}