package com.example.sanalgardrobum.data.repository

import android.content.Context
import android.net.Uri
import android.util.Log
import com.example.sanalgardrobum.data.remote.api.TryOnApiService
import com.example.sanalgardrobum.data.util.Resource
import com.example.sanalgardrobum.data.util.uriToFile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

private const val TAG = "TryOnRepository"

/**
 * TryOnRepository implementasyonu.
 *
 * Akış:
 * 1. emit(Loading) — UI'a yüklenme göster
 * 2. content:// URI'lerini cache'deki fiziksel File'lara dönüştür
 * 3. Dosyaları MultipartBody.Part'a çevir
 * 4. FastAPI'ye multipart POST isteği at
 * 5. Gelen ResponseBody baytlarını (FileResponse) cache klasörüne yaz
 * 6. emit(Success(dosyaYolu)) veya emit(Error(mesaj))
 */
class TryOnRepositoryImpl @Inject constructor(
    private val apiService: TryOnApiService,
    private val context: Context
) : TryOnRepository {

    override fun generateTryOn(
        personImageUri: String,
        garmentImageUri: String
    ): Flow<Resource<String>> = flow {

        emit(Resource.Loading)

        try {
            // ─── 1. URI → File dönüşümü ──────────────────────────────────
            Log.d(TAG, "=== TRY-ON İSTEĞİ HAZIRLANIYOR ===")
            Log.d(TAG, "Person URI: $personImageUri")
            Log.d(TAG, "Garment URI: $garmentImageUri")

            val personUri = Uri.parse(personImageUri)
            val garmentUri = Uri.parse(garmentImageUri)

            val personFile = uriToFile(context, personUri, "person_")
                ?: throw IllegalStateException("Kullanıcı fotoğrafı dosyaya dönüştürülemedi: $personImageUri")

            val garmentFile = uriToFile(context, garmentUri, "garment_")
                ?: throw IllegalStateException("Kıyafet fotoğrafı dosyaya dönüştürülemedi: $garmentImageUri")

            Log.d(TAG, "Dosya dönüşümleri tamamlandı:")
            Log.d(TAG, "  Person: ${personFile.absolutePath} (${personFile.length()} bytes)")
            Log.d(TAG, "  Garment: ${garmentFile.absolutePath} (${garmentFile.length()} bytes)")

            // ─── 2. File → MultipartBody.Part ────────────────────────────
            val personPart = MultipartBody.Part.createFormData(
                name = "human_img",
                filename = personFile.name,
                body = personFile.asRequestBody("image/*".toMediaTypeOrNull())
            )

            val garmentPart = MultipartBody.Part.createFormData(
                name = "garm_img",
                filename = garmentFile.name,
                body = garmentFile.asRequestBody("image/*".toMediaTypeOrNull())
            )

            Log.d(TAG, "=== FASTAPI'YE İSTEK ATILIYOR ===")
            Log.d(TAG, "Endpoint: POST /generate-tryon")
            Log.d(TAG, "Content-Type: multipart/form-data")

            // ─── 3. API çağrısı ──────────────────────────────────────────
            val response = apiService.tryOn(personPart, garmentPart)

            Log.d(TAG, "=== FASTAPI CEVABI ===")
            Log.d(TAG, "HTTP Status: ${response.code()} ${response.message()}")

            if (response.isSuccessful && response.body() != null) {
                // FastAPI FileResponse'u ham bayt olarak geliyor.
                // Bunu geçici bir dosyaya yazıp yolunu döndürüyoruz.
                val outputDir = File(context.cacheDir, "tryon_results").apply { mkdirs() }
                val outputFile = File.createTempFile(
                    "tryon_result_",
                    ".jpg",
                    outputDir
                )
                val bodyBytes = response.body()!!.bytes()
                outputFile.writeBytes(bodyBytes)

                Log.d(TAG, "Sonuç dosyası kaydedildi: ${outputFile.absolutePath} (${bodyBytes.size} bytes)")
                emit(Resource.Success(outputFile.absolutePath))
            } else {
                val errorBody = response.errorBody()?.string() ?: "Bilinmeyen hata"
                Log.e(TAG, "API Hatası: ${response.code()} - $errorBody")
                emit(Resource.Error("Sunucu hatası: ${response.code()} - ${response.message()}"))
            }

            // Temp dosyalarını temizle
            personFile.delete()
            garmentFile.delete()

        } catch (e: Exception) {
            Log.e(TAG, "=== BAĞLANTI HATASI ===", e)
            Log.e(TAG, "Hata: ${e.javaClass.simpleName} - ${e.localizedMessage}")
            emit(Resource.Error("Bağlantı hatası: ${e.localizedMessage}", e))
        }
    }.flowOn(Dispatchers.IO)
}
