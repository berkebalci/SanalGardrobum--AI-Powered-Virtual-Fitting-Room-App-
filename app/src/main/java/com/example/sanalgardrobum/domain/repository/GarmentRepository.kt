package com.example.sanalgardrobum.domain.repository

import android.net.Uri
import com.example.sanalgardrobum.domain.model.Garment
import kotlinx.coroutines.flow.Flow

interface GarmentRepository {
    fun getGarments(userId: String): Flow<List<Garment>>
    fun getGarmentsByCategory(userId: String, category: String): Flow<List<Garment>>
    suspend fun addGarment(userId: String, uri: Uri, name: String, category: String): Long
    suspend fun deleteGarment(garment: Garment)
}
