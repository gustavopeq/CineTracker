package com.projects.moviemanager.core

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import com.projects.moviemanager.BuildConfig
import timber.log.Timber

@HiltAndroidApp
class CoreApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // Init Timber
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
