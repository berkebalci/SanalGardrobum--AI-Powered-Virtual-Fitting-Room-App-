package com.example.sanalgardrobum.domain.usecase.auth

import com.example.sanalgardrobum.domain.model.User
import com.example.sanalgardrobum.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAuthStateUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke(): Flow<User?> = authRepository.getAuthState()
}
