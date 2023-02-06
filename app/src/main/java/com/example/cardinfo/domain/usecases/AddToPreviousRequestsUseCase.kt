package com.example.cardinfo.domain.usecases

import com.example.cardinfo.domain.entity.CardInfo
import com.example.cardinfo.domain.repository.CardInfoRepository

class AddToPreviousRequestsUseCase(private val repository: CardInfoRepository) {
    operator fun invoke(cardInfo: CardInfo) {
        repository.addToPreviousRequests(cardInfo)
    }
}