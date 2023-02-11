package com.example.cardinfo.domain.repository

import androidx.lifecycle.LiveData
import com.example.cardinfo.domain.entity.CardInfo
import com.example.cardinfo.domain.entity.Result

interface CardInfoRepository {

    suspend fun getCardInfo(bin: String): Result

    fun getPreviousRequests(): LiveData<List<CardInfo>>

    suspend fun addToPreviousRequests(cardInfo: CardInfo)

    suspend fun removeFromPreviousRequests(bin: String)

}