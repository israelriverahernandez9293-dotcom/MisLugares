package com.mislugares.core

import android.app.Application
import com.google.firebase.FirebaseApp
import com.mislugares.BuildConfig

class MisLugaresApp : Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        PlacesInitializer.initialize(this, BuildConfig.PLACES_API_KEY)
    }
}
