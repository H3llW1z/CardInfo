package com.example.cardinfo.data.implementation

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.cardinfo.data.api.ApiService
import com.example.cardinfo.data.db.CardInfoDao
import com.example.cardinfo.data.toDbModel
import com.example.cardinfo.data.toEntity
import com.example.cardinfo.domain.entity.CardInfo
import com.example.cardinfo.domain.entity.Result
import com.example.cardinfo.domain.repository.CardInfoRepository
import javax.inject.Inject


class CardInfoRepositoryImpl @Inject constructor(
    private val cardInfoDao: CardInfoDao,
    private val apiService: ApiService
) : CardInfoRepository {

    override suspend fun getCardInfo(bin: String): Result {
        try {
            val response = apiService.getCardInfo(bin)
            if (response.isSuccessful) {
                response.body()?.let {
                    return Result.Success(it.toEntity(bin))
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
        return cardInfoDao.getPreviousRequests().map { list ->
            list.map { it.toEntity() }
        }
    }

    override suspend fun addToPreviousRequests(cardInfo: CardInfo) {
        cardInfoDao.addCardInfo(cardInfo.toDbModel())
    }

    override suspend fun removeFromPreviousRequests(bin: String) {
        cardInfoDao.deleteCardInfo(bin)
    }

    private fun mapHttpCodeToErrorEntity(code: Int): Result {
        return when (code) {
            401, 403 -> throw RuntimeException("Auth error")
            404 -> Result.Error.CardNotFound
            in 400..451 -> throw RuntimeException("Client error")
            in 500..526 -> Result.Error.ServerError
            else -> Result.Error.NetworkError
        }
    }
}