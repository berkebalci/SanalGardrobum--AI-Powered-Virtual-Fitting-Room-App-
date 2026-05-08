package com.example.sanalgardrobum.data.util

import android.content.Context
import android.net.Uri
import android.util.Log
import java.io.File
import java.io.FileOutputStream

private const val TAG = "FileUtils"

/**
 * content:// URI'sini gerçek bir File nesnesine dönüştürür.
 *
 * Android'de galeri/kamera seçimlerinden dönen URI'ler content:// şemasındadır.
 * Retrofit'e MultipartBody.Part olarak gönderebilmek için bu URI'nin içeriğini
 * uygulama cache dizininde fiziksel bir dosyaya kopyalamamız gerekir.
 *
 * @param context Uygulama context'i (ContentResolver için)
 * @param uri Dönüştürülecek content:// URI
 * @param prefix Geçici dosyanın ön eki (örn. "person_", "garment_")
 * @return Oluşturulan geçici File veya hata durumunda null
 */
fun uriToFile(context: Context, uri: Uri, prefix: String = "upload_"): File? {
    return try {
        val imagesDir = File(context.cacheDir, "api_uploads").apply { mkdirs() }
        val tempFile = File.createTempFile(prefix, ".jpg", imagesDir)

        context.contentResolver.openInputStream(uri)?.use { inputStream ->
            FileOutputStream(tempFile).use { outputStream ->
                inputStream.copyTo(outputStream)
            }
        }

        Log.d(TAG, "URI → File dönüşümü başarılı: ${uri} → ${tempFile.absolutePath} (${tempFile.length()} bytes)")
        tempFile
    } catch (e: Exception) {
        Log.e(TAG, "URI → File dönüşümü başarısız: ${uri}", e)
        null
    }
}
