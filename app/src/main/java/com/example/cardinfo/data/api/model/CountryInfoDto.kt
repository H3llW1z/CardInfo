package com.example.cardinfo.data.api.model

import com.google.gson.annotations.SerializedName

data class CountryInfoDto(
    val numeric: String?,
    @SerializedName("alpha2")
    val shortName: String?,
    val name: String?,
    val emoji: String?,
    val currency: String?,
    val latitude: Double?,
    val longitude: Double?
)
