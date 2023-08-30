package com.withings.mycomposeandblepractice.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface ImageDao {

    @Upsert
    suspend fun upsertAll(images: List<ImageEntity>)

    @Query("SELECT * FROM image")
    fun pagingSource(): PagingSource<Int, ImageEntity>

    @Query("DELETE FROM image")
    suspend fun clearAll()
}