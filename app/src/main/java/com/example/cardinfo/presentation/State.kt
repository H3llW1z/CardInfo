package com.example.cardinfo.presentation

import com.example.cardinfo.domain.entity.CardInfo

sealed class State {
    object NetworkError : State()
    object ServerError: State()
    object CardNotFound: State()
    object Progress: State()
    object InputError: State()
    class Success(val data: CardInfo): State()
}


