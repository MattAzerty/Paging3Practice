package com.withings.mycomposeandblepractice.data.mappers

import com.withings.mycomposeandblepractice.data.local.ImageEntity
import com.withings.mycomposeandblepractice.data.remote.PixabayDto

fun PixabayDto.toPictureEntity(): ImageEntity {
    return ImageEntity(
        id = 0,
        webformatURL = webformatURL,
    )
}