package com.example.cardinfo.data.api

import com.example.cardinfo.data.api.model.CardInfoDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("{bin}")
    suspend fun getCardInfo(@Path("bin") bin: String): Response<CardInfoDto>
}