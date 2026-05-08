package com.example.sanalgardrobum.data.remote.api

import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

/**
 * Retrofit API arayüzü.
 *
 * Android → (Multipart) → FastAPI → (Gradio) → IDM-VTON → FastAPI → (FileResponse) → Android
 *
 * FastAPI endpoint'i iki UploadFile alır ve FileResponse döner.
 * Bu yüzden istek @Multipart, dönüş tipi ise raw ResponseBody (fotoğraf baytları)'dır.
 */
interface TryOnApiService {

    @Multipart
    @POST("generate-tryon")
    suspend fun generateTryOn(
        @Part personImage: MultipartBody.Part,
        @Part garmentImage: MultipartBody.Part
    ): Response<ResponseBody>
}
