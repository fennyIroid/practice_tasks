package com.example.practice_tasks

import android.app.Application
import com.google.android.libraries.places.api.Places
import com.google.firebase.BuildConfig
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class MyApp: Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        if (!Places.isInitialized()){
            Places.initialize(
                applicationContext,
                getString(R.string.google_maps_key)
            )
        }
    }

}