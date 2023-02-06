package com.example.cardinfo.domain.usecases

import com.example.cardinfo.domain.repository.CardInfoRepository

class GetPreviousRequestsUseCase(private val repository: CardInfoRepository) {
    operator fun invoke() {
        repository.getPreviousRequests()
    }
}