package com.example.cardinfo.domain.usecases

import com.example.cardinfo.domain.repository.CardInfoRepository

class GetCardInfoUseCase(private val repository: CardInfoRepository) {
    operator fun invoke(bin: String) {
        repository.getCardInfo(bin)
    }
}