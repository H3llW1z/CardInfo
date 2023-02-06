package com.example.cardinfo.domain.usecases

import com.example.cardinfo.domain.repository.CardInfoRepository

class RemoveFromPreviousRequestsUseCase(private val repository: CardInfoRepository) {
    operator fun invoke(id: Int) {
        repository.removeFromPreviousRequests(id)
    }
}