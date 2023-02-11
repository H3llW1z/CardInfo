package com.example.cardinfo.domain.usecases

import com.example.cardinfo.domain.repository.CardInfoRepository
import javax.inject.Inject

class RemoveFromPreviousRequestsUseCase @Inject constructor(private val repository: CardInfoRepository) {
    suspend operator fun invoke(bin: String) {
        repository.removeFromPreviousRequests(bin)
    }
}