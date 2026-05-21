package com.example.sanalgardrobum.domain.usecase.auth

import android.content.Context
import com.example.sanalgardrobum.domain.model.User
import com.example.sanalgardrobum.domain.repository.AuthRepository
import javax.inject.Inject

class SignInWithGoogleUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(context: Context): Result<User> =
        authRepository.signInWithGoogle(context)
}
