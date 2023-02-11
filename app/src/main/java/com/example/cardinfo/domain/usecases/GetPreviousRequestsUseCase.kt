package com.example.cardinfo.domain.usecases

import androidx.lifecycle.LiveData
import com.example.cardinfo.domain.entity.CardInfo
import com.example.cardinfo.domain.repository.CardInfoRepository
import javax.inject.Inject

class GetPreviousRequestsUseCase @Inject constructor(private val repository: CardInfoRepository) {
    operator fun invoke(): LiveData<List<CardInfo>> {
        return repository.getPreviousRequests()
    }
}