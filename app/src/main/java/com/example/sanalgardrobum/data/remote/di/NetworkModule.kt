package com.example.sanalgardrobum.data.remote.di

import com.example.sanalgardrobum.data.remote.api.TryOnApiService
import com.example.sanalgardrobum.data.repository.TryOnRepository
import com.example.sanalgardrobum.data.repository.TryOnRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    /**
     * IDM-VTON modeli uzun sürebilir (Gradio inference).
     * Read/Write/Connect timeout değerleri buna göre uzatıldı.
     */
    private const val BASE_URL = "http://10.0.2.2:8000/" // Emülatör → localhost
    private const val TIMEOUT_SECONDS = 120L

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideTryOnApiService(retrofit: Retrofit): TryOnApiService {
        return retrofit.create(TryOnApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideTryOnRepository(
        apiService: TryOnApiService
    ): TryOnRepository {
        return TryOnRepositoryImpl(apiService)
    }
}
