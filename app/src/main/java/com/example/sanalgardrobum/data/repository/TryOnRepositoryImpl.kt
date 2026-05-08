package com.example.sanalgardrobum.data.repository

import com.example.sanalgardrobum.data.remote.api.TryOnApiService
import com.example.sanalgardrobum.data.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

/**
 * TryOnRepository implementasyonu.
 *
 * Akış:
 * 1. emit(Loading) — UI'a yüklenme göster
 * 2. Yerel dosyaları MultipartBody.Part'a dönüştür
 * 3. FastAPI'ye multipart POST isteği at
 * 4. Gelen ResponseBody baytlarını (FileResponse) cache klasörüne yaz
 * 5. emit(Success(dosyaYolu)) veya emit(Error(mesaj))
 */
class TryOnRepositoryImpl @Inject constructor(
    private val apiService: TryOnApiService
) : TryOnRepository {

    override fun generateTryOn(
        personImagePath: String,
        garmentImagePath: String
    ): Flow<Resource<String>> = flow {

        emit(Resource.Loading)

        try {
            val personFile = File(personImagePath)
            val garmentFile = File(garmentImagePath)

            val personPart = MultipartBody.Part.createFormData(
                name = "person_image",
                filename = personFile.name,
                body = personFile.asRequestBody("image/*".toMediaTypeOrNull())
            )

            val garmentPart = MultipartBody.Part.createFormData(
                name = "garment_image",
                filename = garmentFile.name,
                body = garmentFile.asRequestBody("image/*".toMediaTypeOrNull())
            )

            val response = apiService.generateTryOn(personPart, garmentPart)

            if (response.isSuccessful && response.body() != null) {
                // FastAPI FileResponse'u ham bayt olarak geliyor.
                // Bunu geçici bir dosyaya yazıp yolunu döndürüyoruz.
                val outputFile = File.createTempFile(
                    "tryon_result_",
                    ".jpg",
                    personFile.parentFile // Cache klasörü olarak parent dir kullan
                )
                outputFile.writeBytes(response.body()!!.bytes())
                emit(Resource.Success(outputFile.absolutePath))
            } else {
                emit(Resource.Error("Sunucu hatası: ${response.code()} - ${response.message()}"))
            }

        } catch (e: Exception) {
            emit(Resource.Error("Bağlantı hatası: ${e.localizedMessage}", e))
        }
    }
}
