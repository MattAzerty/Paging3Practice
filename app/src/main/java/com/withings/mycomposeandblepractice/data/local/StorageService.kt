package com.withings.mycomposeandblepractice.data.local

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Environment
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import kotlinx.coroutines.delay
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.time.Duration.Companion.seconds

@Singleton
class StorageService @Inject constructor(
    private val context: Context,
) {

    //Store pictures to download folder
    suspend fun storeEstatePictureEntities(
        pictures: List<ImageEntity>,
    ) {
        val folder: File =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        //Each pictures are stored
        pictures.forEach { imageEntity ->
            val file = File(folder, "${imageEntity.id}.jpg")
            val outputStream = FileOutputStream(file)
            outputStream.use {
                getBitmap(imageEntity.webformatURL).compress(
                    Bitmap.CompressFormat.JPEG,
                    100,
                    it
                )
            }
            outputStream.close()
        }
    }

    //url to bitmap with Coil to avoid another internet use https://coil-kt.github.io/coil/image_requests/
    private suspend fun getBitmap(url: String): Bitmap {
        val loading = ImageLoader(context)
        val request = ImageRequest.Builder(context)
            .data(url)
            .build()
        val result = (loading.execute(request) as SuccessResult).drawable
        return (result as BitmapDrawable).bitmap
    }
}