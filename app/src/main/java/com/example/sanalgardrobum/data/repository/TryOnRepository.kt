package com.example.sanalgardrobum.data.repository

import com.example.sanalgardrobum.data.util.Resource
import kotlinx.coroutines.flow.Flow

/**
 * TryOn veri işlemleri için sözleşme arayüzü.
 *
 * @param personImageUri Kişi fotoğrafının content:// URI string'i
 * @param garmentImageUri Kıyafet fotoğrafının content:// URI string'i
 * @return Üretilen sonuç fotoğrafının cihazda kaydedildiği geçici dosya yolunu yayınlayan Flow
 */
interface TryOnRepository {
    fun generateTryOn(
        personImageUri: String,
        garmentImageUri: String
    ): Flow<Resource<String>>
}