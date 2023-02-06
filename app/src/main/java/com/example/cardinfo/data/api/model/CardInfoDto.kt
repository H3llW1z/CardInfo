package com.example.cardinfo.data.api.model

import com.google.gson.annotations.SerializedName

data class CardInfoDto(
    @SerializedName("number")
    val cardNumberInfoDto: CardNumberInfoDto?,
    val scheme: String?,
    val type: String?,
    val brand: String?,
    @SerializedName("prepaid")
    val isPrepaid: Boolean?,
    @SerializedName("country")
    val countryInfoDto: CountryInfoDto?,
    @SerializedName("bank")
    val bankInfoDto: BankInfoDto?
)
