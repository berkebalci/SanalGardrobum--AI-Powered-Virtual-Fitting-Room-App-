package com.example.sanalgardrobum.domain.repository

import android.content.Context
import com.example.sanalgardrobum.domain.model.User
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun getAuthState(): Flow<User?>
    fun getCurrentUser(): User?
    suspend fun signInWithGoogle(context: Context): Result<User>
    fun signOut()
}
