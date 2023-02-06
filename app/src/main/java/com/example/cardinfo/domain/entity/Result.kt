package com.example.cardinfo.domain.entity

sealed class Result {
    class Success(val data: CardInfo): Result()
    sealed class Error: Result() {
        object CardNotFound : Error()
        object NetworkError: Error()
        object ServerError: Error()

    }
}





