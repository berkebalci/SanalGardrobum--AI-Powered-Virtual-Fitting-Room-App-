package com.example.sanalgardrobum.data.util

/**
 * Generic bir sarmalayıcı sınıf. Ağ veya veri katmanından gelen
 * Loading, Success ve Error durumlarını UI katmanına taşır.
 */
sealed class Resource<out T> {
    data object Loading : Resource<Nothing>()
    data class Success<T>(val data: T) : Resource<T>()
    data class Error(val message: String, val exception: Exception? = null) : Resource<Nothing>()
}
