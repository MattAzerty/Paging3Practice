package com.withings.mycomposeandblepractice.data.remote

data class PixabayResponse(
    val total: Int,
    val totalHits: Int,
    val hits: List<PixabayDto>
)