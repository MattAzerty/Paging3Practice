package com.withings.mycomposeandblepractice.data.local

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "image")
data class ImageEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val webformatURL: String,
    //val myChoice: Boolean
)