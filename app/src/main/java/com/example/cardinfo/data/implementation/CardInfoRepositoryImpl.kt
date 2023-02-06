package com.example.cardinfo.data.implementation

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.cardinfo.data.api.ApiFactory
import com.example.cardinfo.data.db.AppDatabase
import com.example.cardinfo.data.toDbModel
import com.example.cardinfo.data.toEntity
import com.example.cardinfo.domain.entity.CardInfo
import com.example.cardinfo.domain.entity.Result
import com.example.cardinfo.domain.repository.CardInfoRepository


class CardInfoRepositoryImpl(application: Application): CardInfoRepository {

    private val apiService = ApiFactory.apiService
    private val cardInfoDao = AppDatabase.getInstance(application).cardInfoDao()

    override suspend fun getCardInfo(bin: String): Result {
        try {
            val response = apiService.getCardInfo(bin)
            if (response.isSuccessful) {
                response.body()?.let {
                    return Result.Success(it.toEntity())
                }
                return Result.Error.CardNotFound
            } else {
                return mapHttpCodeToErrorEntity(response.code())
            }
        } catch (e: Exception) {
            return Result.Error.NetworkError
        }
    }

    override fun getPreviousRequests(): LiveData<List<CardInfo>> {
        return Transformations.map(
            cardInfoDao.getPreviousRequests()
        ) { list ->
            list.map {it.toEntity()}
        }
    }

    override suspend fun addToPreviousRequests(cardInfo: CardInfo) {
        cardInfoDao.addCardInfo(cardInfo.toDbModel())
    }

    override suspend fun removeFromPreviousRequests(id: Long) {
        cardInfoDao.deleteCardInfo(id)
    }

    private fun mapHttpCodeToErrorEntity(code: Int): Result {
        return when (code) {
            401, 403 -> throw RuntimeException("Auth error")
            in 400..451 -> throw RuntimeException("Client error")
            in 500..526 -> Result.Error.ServerError
            else -> Result.Error.NetworkError
        }
    }
}