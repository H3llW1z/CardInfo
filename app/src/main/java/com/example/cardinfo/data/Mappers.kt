package com.example.cardinfo.data

import com.example.cardinfo.data.api.model.BankInfoDto
import com.example.cardinfo.data.api.model.CardInfoDto
import com.example.cardinfo.data.api.model.CountryInfoDto
import com.example.cardinfo.data.db.model.CardInfoDbModel
import com.example.cardinfo.domain.entity.BankInfo
import com.example.cardinfo.domain.entity.CardInfo
import com.example.cardinfo.domain.entity.CountryInfo

fun CardInfoDto.toEntity(): CardInfo {
    return CardInfo(
        id = 0,
        numberLength = this.cardNumberInfoDto?.length ?: -1,
        isLuhn = this.cardNumberInfoDto?.luhn ?: false,
        scheme = this.scheme ?: "",
        type = this.type ?: "",
        brand = this.brand ?: "",
        isPrepaid = this.isPrepaid ?: false,
        countryInfo = countryInfoDto?.toEntity() ?: CountryInfo(
            "",
            "",
            "",
            "",
            "",
            -1.0,
            -1.0,
        ),
        bankInfo = bankInfoDto?.toEntity() ?: BankInfo("", "", "", ""),
    )
}

fun CountryInfoDto.toEntity(): CountryInfo {
    return CountryInfo(
        numeric = numeric ?: "",
        shortName = shortName ?: "",
        name = name ?: "",
        emoji = emoji ?: "",
        currency = currency ?: "",
        latitude = latitude ?: -1.0,
        longitude = longitude ?: -1.0
    )
}

fun BankInfoDto.toEntity(): BankInfo {
    return BankInfo(
        name = name ?: "",
        url = url ?: "",
        phone = phone ?: "",
        city = city ?: ""
    )
}

fun CardInfoDbModel.toEntity(): CardInfo {
    return CardInfo(
        id = id,
        numberLength = numberLength,
        isLuhn = isLuhn,
        scheme = scheme,
        type = type,
        brand = brand,
        isPrepaid = isPrepaid,
        countryInfo = CountryInfo(numeric, shortCountryName, countryName, emoji, currency, latitude, longitude),
        bankInfo = BankInfo(bankName, bankUrl, bankPhone, bankCity)
    )
}

fun CardInfo.toDbModel(): CardInfoDbModel {
    return CardInfoDbModel(
        id = 0,
        numberLength = numberLength,
        isLuhn = isLuhn,
        scheme = scheme,
        type = type,
        brand = brand,
        isPrepaid = isPrepaid,
        numeric = countryInfo.numeric,
        shortCountryName = countryInfo.shortName,
        countryName =  countryInfo.name,
        emoji = countryInfo.emoji,
        currency = countryInfo.currency,
        latitude = countryInfo.latitude,
        longitude = countryInfo.longitude,
        bankName = bankInfo.name,
        bankUrl = bankInfo.url,
        bankPhone = bankInfo.phone,
        bankCity = bankInfo.city
    )
}
