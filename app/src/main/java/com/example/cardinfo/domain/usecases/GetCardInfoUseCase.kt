package com.example.cardinfo.domain.usecases

import com.example.cardinfo.domain.repository.CardInfoRepository
import com.example.cardinfo.domain.entity.Result
import javax.inject.Inject

class GetCardInfoUseCase @Inject constructor(private val repository: CardInfoRepository) {
    suspend operator fun invoke(bin: String): Result {
        return repository.getCardInfo(bin)
    }
}