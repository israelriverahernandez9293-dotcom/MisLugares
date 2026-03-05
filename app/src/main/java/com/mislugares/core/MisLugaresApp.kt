package com.mislugares.core

import android.app.Application
import com.google.firebase.FirebaseApp

class MisLugaresApp : Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
    }
}
