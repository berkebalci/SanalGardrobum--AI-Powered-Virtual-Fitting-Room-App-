package com.example.sanalgardrobum.domain.usecase.auth

import com.example.sanalgardrobum.domain.model.User
import com.example.sanalgardrobum.domain.repository.AuthRepository
import javax.inject.Inject

class GetCurrentUserUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke(): User? = authRepository.getCurrentUser()
}
