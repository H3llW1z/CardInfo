package com.example.cardinfo.domain.usecases

import com.example.cardinfo.domain.entity.CardInfo
import com.example.cardinfo.domain.repository.CardInfoRepository
import javax.inject.Inject

class AddToPreviousRequestsUseCase @Inject constructor(private val repository: CardInfoRepository) {
    suspend operator fun invoke(cardInfo: CardInfo) {
        repository.addToPreviousRequests(cardInfo)
    }
}