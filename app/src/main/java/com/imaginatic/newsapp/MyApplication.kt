package com.imaginatic.newsapp

import android.app.Application
import com.imaginatic.newsapp.di.AppComponent
import com.imaginatic.newsapp.di.DaggerAppComponent
import com.jakewharton.threetenabp.AndroidThreeTen
import com.newsapp.core.di.DaggerCoreComponent

class MyApplication: Application() {
    private val coreComponent by lazy {
        DaggerCoreComponent.builder().context(this).build()
    }

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.builder().coreComponent(coreComponent).build()
    }

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
    }
}