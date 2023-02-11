package com.example.cardinfo.di

import android.app.Application
import com.example.cardinfo.presentation.MainActivity
import dagger.BindsInstance
import dagger.Component


@Component(modules = [DataModule::class, ViewModelModule::class])
@ApplicationScope
interface ApplicationComponent {

    fun inject(activity: MainActivity)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: Application): ApplicationComponent
    }
}