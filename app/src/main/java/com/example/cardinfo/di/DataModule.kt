package com.example.cardinfo.di

import android.app.Application
import com.example.cardinfo.data.api.ApiFactory
import com.example.cardinfo.data.api.ApiService
import com.example.cardinfo.data.db.AppDatabase
import com.example.cardinfo.data.db.CardInfoDao
import com.example.cardinfo.data.implementation.CardInfoRepositoryImpl
import com.example.cardinfo.domain.repository.CardInfoRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {

    @Binds
    @ApplicationScope
    fun bindCardInfoRepository(impl: CardInfoRepositoryImpl): CardInfoRepository

    companion object {
        @Provides
        @ApplicationScope
        fun provideCardInfoDao(application: Application): CardInfoDao {
            return AppDatabase.getInstance(application).cardInfoDao()
        }

        @Provides
        @ApplicationScope
        fun provideApiService(): ApiService {
            return ApiFactory.apiService
        }
    }
}