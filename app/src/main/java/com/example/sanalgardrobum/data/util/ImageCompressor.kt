package com.example.sanalgardrobum.data.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

/**
 * URI'den bitmap okuyup WebP formatında sıkıştırarak
 * context.filesDir/garments/{userId}/ altına kaydeder.
 * Room'da sadece döndürülen dosya yolu saklanır.
 */
@Singleton
class ImageCompressor @Inject constructor(
    @ApplicationContext private val context: Context
) {

    /**
     * @return Kaydedilen .webp dosyasının mutlak yolu
     */
    suspend fun compressToWebP(uri: Uri, userId: String): String =
        withContext(Dispatchers.IO) {
            val inputStream = context.contentResolver.openInputStream(uri)
                ?: throw IOException("Cannot open input stream for URI: $uri")

            val bitmap = inputStream.use { BitmapFactory.decodeStream(it) }
                ?: throw IOException("Cannot decode bitmap from URI: $uri")

            val userDir = File(context.filesDir, "garments/$userId")
            if (!userDir.exists()) userDir.mkdirs()

            val fileName = "${UUID.randomUUID()}.webp"
            val file = File(userDir, fileName)

            FileOutputStream(file).use { fos ->
                bitmap.compress(Bitmap.CompressFormat.WEBP_LOSSY, 80, fos)
            }
            bitmap.recycle()

            file.absolutePath
        }
}
