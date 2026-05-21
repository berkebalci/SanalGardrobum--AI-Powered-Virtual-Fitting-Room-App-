package com.example.sanalgardrobum.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.sanalgardrobum.data.local.dao.GarmentDao
import com.example.sanalgardrobum.data.local.entity.GarmentEntity

@Database(
    entities = [GarmentEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun garmentDao(): GarmentDao
}
