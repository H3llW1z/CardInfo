package com.example.cardinfo.domain.entity

data class CountryInfo(
    val numeric: String,
    val shortName: String,
    val name: String,
    val emoji: String,
    val currency: String,
    val latitude: Double,
    val longitude: Double
)
