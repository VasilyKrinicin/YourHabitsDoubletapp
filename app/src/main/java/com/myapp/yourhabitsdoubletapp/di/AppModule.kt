package com.myapp.yourhabitsdoubletapp.di

import dagger.Module
import javax.inject.Singleton


@Module(subcomponents = [ListHabitComponent::class, EditHabitComponent::class])
class AppModule {

}