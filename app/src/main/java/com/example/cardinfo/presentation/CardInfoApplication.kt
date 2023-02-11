package com.example.cardinfo.presentation

import android.app.Application
import com.example.cardinfo.di.ApplicationComponent
import com.example.cardinfo.di.DaggerApplicationComponent

class CardInfoApplication: Application() {
    val component: ApplicationComponent by lazy {
        DaggerApplicationComponent.factory().create(this)
    }
}