package com.mislugares.core

import android.content.Context
import android.util.Log
import com.google.android.libraries.places.api.Places

object PlacesInitializer {
    private const val TAG = "PlacesInitializer"

    fun initialize(context: Context, apiKey: String) {
        if (apiKey.isBlank()) {
            Log.w(TAG, "PLACES_API_KEY vacío. Places SDK no se inicializa.")
            return
        }

        if (!Places.isInitialized()) {
            Places.initialize(context.applicationContext, apiKey)
        }
    }
}
