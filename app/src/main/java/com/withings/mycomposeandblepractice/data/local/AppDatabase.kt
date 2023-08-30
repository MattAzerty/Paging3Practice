package com.withings.mycomposeandblepractice.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [ImageEntity::class],
    version = 1
)
abstract class AppDatabase: RoomDatabase() {
    abstract val imageDao: ImageDao
}