package com.withings.mycomposeandblepractice.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface PixabayApi {

    @GET("api/")
    suspend fun getImages(
        @Query("key") apiKey: String = "18021445-326cf5bcd3658777a9d22df6f",
        @Query("q") query: String,
        @Query("image_type") imageType: String = "photo",
        @Query("page") page: Int,
        @Query("per_page") pageCount: Int = 20
    ): PixabayResponse

    companion object {
        const val BASE_URL = "https://pixabay.com/"
    }
}