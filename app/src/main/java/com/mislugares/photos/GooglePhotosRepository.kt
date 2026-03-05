package com.mislugares.photos

import com.mislugares.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class GooglePhotosRepository(
    private val api: GooglePhotosApi = Retrofit.Builder()
        .baseUrl(BuildConfig.GOOGLE_PHOTOS_BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .client(
            OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
                .build()
        )
        .build()
        .create(GooglePhotosApi::class.java)
) {
    suspend fun fetchAlbums(accessToken: String): AlbumsResponse {
        require(accessToken.isNotBlank()) { "Google access token cannot be blank" }
        return api.getAlbums("Bearer $accessToken")
    }
}
