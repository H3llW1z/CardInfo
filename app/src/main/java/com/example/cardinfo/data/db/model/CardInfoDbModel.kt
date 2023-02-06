package com.example.cardinfo.data.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "card_info")
data class CardInfoDbModel(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val numberLength: Int,
    val isLuhn: Boolean,
    val scheme: String,
    val type: String,
    val brand: String,
    val isPrepaid: Boolean,
    val numeric: String,
    val shortCountryName: String,
    val countryName: String,
    val emoji: String,
    val currency: String,
    val latitude: Double,
    val longitude: Double,
    val bankName: String,
    val bankUrl: String,
    val bankPhone: String,
    val bankCity: String
)
