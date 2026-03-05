package com.mislugares.photos

import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.Scope
import com.mislugares.BuildConfig

object GooglePhotosAuthConfig {
    val photosReadScope: Scope = Scope(BuildConfig.GOOGLE_PHOTOS_READ_SCOPE)

    fun signInOptions(): GoogleSignInOptions {
        return GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestScopes(photosReadScope)
            .build()
    }
}
