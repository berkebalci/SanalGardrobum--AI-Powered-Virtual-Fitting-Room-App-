package com.example.sanalgardrobum.data.repository

import android.net.Uri
import com.example.sanalgardrobum.data.local.dao.GarmentDao
import com.example.sanalgardrobum.data.local.entity.GarmentEntity
import com.example.sanalgardrobum.data.util.ImageCompressor
import com.example.sanalgardrobum.domain.mapper.toDomain
import com.example.sanalgardrobum.domain.mapper.toEntity
import com.example.sanalgardrobum.domain.model.Garment
import com.example.sanalgardrobum.domain.repository.GarmentRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GarmentRepositoryImpl @Inject constructor(
    private val garmentDao: GarmentDao,
    private val imageCompressor: ImageCompressor
) : GarmentRepository {

    override fun getGarments(userId: String): Flow<List<Garment>> =
        garmentDao.getGarmentsByUser(userId).map { entities ->
            entities.map { it.toDomain() }
        }

    override fun getGarmentsByCategory(userId: String, category: String): Flow<List<Garment>> =
        garmentDao.getGarmentsByCategory(userId, category).map { entities ->
            entities.map { it.toDomain() }
        }

    override suspend fun addGarment(
        userId: String,
        uri: Uri,
        name: String,
        category: String
    ): Long {
        val webpPath = imageCompressor.compressToWebP(uri, userId)
        val entity = GarmentEntity(
            userId = userId,
            category = category,
            imagePath = webpPath,
            name = name
        )
        return garmentDao.insertGarment(entity)
    }

    override suspend fun deleteGarment(garment: Garment) {
        // Yerel dosyayı sil
        val file = File(garment.imagePath)
        if (file.exists()) file.delete()
        // Room kaydını sil
        garmentDao.deleteGarment(garment.toEntity())
    }
}
