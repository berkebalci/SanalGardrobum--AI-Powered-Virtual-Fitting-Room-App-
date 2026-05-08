package com.example.sanalgardrobum.data.repository

import com.example.sanalgardrobum.data.util.Resource
import kotlinx.coroutines.flow.Flow

/**
 * TryOn veri işlemleri için sözleşme arayüzü.
 *
 * @param personImagePath Kişi fotoğrafının cihazındaki yerel dosya yolu
 * @param garmentImagePath Kıyafet fotoğrafının cihazındaki yerel dosya yolu
 * @return Üretilen sonuç fotoğrafının cihazda kaydedildiği geçici dosya yolunu yayınlayan Flow
 */
interface TryOnRepository {
    fun generateTryOn(
        personImagePath: String,
        garmentImagePath: String
    ): Flow<Resource<String>>
}