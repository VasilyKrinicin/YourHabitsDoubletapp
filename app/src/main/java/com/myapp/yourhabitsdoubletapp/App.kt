package com.myapp.yourhabitsdoubletapp

import android.app.Application
import com.myapp.yourhabitsdoubletapp.di.AppComponent
import com.myapp.yourhabitsdoubletapp.di.DaggerAppComponent
import dagger.Component

class App:Application() {

    lateinit var appComponent: AppComponent
    override fun onCreate() {
        super.onCreate()
        appComponent=DaggerAppComponent.factory().create(context = this)


    }
}