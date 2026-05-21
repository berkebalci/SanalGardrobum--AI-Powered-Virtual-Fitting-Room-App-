package com.example.sanalgardrobum.data.repository

import android.content.Context
import com.example.sanalgardrobum.data.auth.FirebaseAuthService
import com.example.sanalgardrobum.domain.mapper.toDomain
import com.example.sanalgardrobum.domain.model.User
import com.example.sanalgardrobum.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuthService: FirebaseAuthService
) : AuthRepository {

    override fun getAuthState(): Flow<User?> =
        firebaseAuthService.getAuthStateFlow().map { firebaseUser ->
            firebaseUser?.toDomain()
        }

    override fun getCurrentUser(): User? =
        firebaseAuthService.getCurrentUser()?.toDomain()

    override suspend fun signInWithGoogle(context: Context): Result<User> = try {
        val authResult = firebaseAuthService.signInWithGoogle(context)
        val user = authResult.user?.toDomain()
            ?: throw IllegalStateException("Firebase user is null after sign-in")
        Result.success(user)
    } catch (e: Exception) {
        Result.failure(e)
    }

    override fun signOut() {
        firebaseAuthService.signOut()
    }
}
