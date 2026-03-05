package com.mislugares.photos

import com.mislugares.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

class GooglePhotosRepository(
    private val api: GooglePhotosApi = provideApi()
) {
    suspend fun fetchAlbums(accessToken: String): AlbumsResponse {
        require(accessToken.isNotBlank()) { "Google access token cannot be blank" }
        return api.getAlbums("Bearer $accessToken")
    }

    companion object {
        private fun provideApi(): GooglePhotosApi {
            val logging = HttpLoggingInterceptor().apply {
                level = if (BuildConfig.DEBUG) {
                    HttpLoggingInterceptor.Level.BASIC
                } else {
                    HttpLoggingInterceptor.Level.NONE
                }
            }

            val client = OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(logging)
                .build()

            return Retrofit.Builder()
                .baseUrl(BuildConfig.GOOGLE_PHOTOS_BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .client(client)
                .build()
                .create(GooglePhotosApi::class.java)
        }
    }
}
