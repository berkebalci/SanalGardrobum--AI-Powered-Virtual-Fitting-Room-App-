package com.example.sanalgardrobum.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.sanalgardrobum.data.local.entity.GarmentEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GarmentDao {

    @Query("SELECT * FROM garments WHERE userId = :userId ORDER BY createdAt DESC")
    fun getGarmentsByUser(userId: String): Flow<List<GarmentEntity>>

    @Query("SELECT * FROM garments WHERE userId = :userId AND category = :category ORDER BY createdAt DESC")
    fun getGarmentsByCategory(userId: String, category: String): Flow<List<GarmentEntity>>

    @Upsert
    suspend fun insertGarment(garment: GarmentEntity): Long

    @Delete
    suspend fun deleteGarment(garment: GarmentEntity)

    @Query("DELETE FROM garments WHERE userId = :userId")
    suspend fun deleteAllByUser(userId: String)
}
