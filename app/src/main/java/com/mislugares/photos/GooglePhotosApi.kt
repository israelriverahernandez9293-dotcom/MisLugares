package com.mislugares.photos

import retrofit2.http.GET
import retrofit2.http.Header

interface GooglePhotosApi {
    @GET("v1/albums")
    suspend fun getAlbums(
        @Header("Authorization") bearerToken: String
    ): AlbumsResponse
}

data class AlbumsResponse(
    val albums: List<AlbumDto> = emptyList()
)

data class AlbumDto(
    val id: String,
    val title: String,
    val productUrl: String?
)
