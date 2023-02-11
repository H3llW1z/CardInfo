package com.example.cardinfo.domain.usecases

import com.example.cardinfo.domain.repository.CardInfoRepository

class RemoveFromPreviousRequestsUseCase(private val repository: CardInfoRepository) {
    suspend operator fun invoke(bin: String) {
        repository.removeFromPreviousRequests(bin)
    }
}