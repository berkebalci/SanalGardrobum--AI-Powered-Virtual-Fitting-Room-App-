package com.example.sanalgardrobum.domain.usecase.auth

import com.example.sanalgardrobum.domain.repository.AuthRepository
import javax.inject.Inject

class SignOutUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke() = authRepository.signOut()
}
