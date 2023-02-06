package com.example.cardinfo.domain.entity

data class CardInfo(
    val id: Long,
    val numberLength: Int,
    val isLuhn: Boolean,
    val scheme: String,
    val type: String,
    val brand: String,
    val isPrepaid: Boolean,
    val countryInfo: CountryInfo,
    val bankInfo: BankInfo
)
