package com.myapp.yourhabitsdoubletapp.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, DomainModule::class, DataModule::class])
interface AppComponent {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun listHabitComponent(): ListHabitComponent.Factory
    fun editHabitComponent(): EditHabitComponent.Factory
}