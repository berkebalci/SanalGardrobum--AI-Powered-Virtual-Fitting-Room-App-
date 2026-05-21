package com.example.sanalgardrobum.domain.usecase.garment

import android.net.Uri
import com.example.sanalgardrobum.domain.repository.GarmentRepository
import javax.inject.Inject

class AddGarmentUseCase @Inject constructor(
    private val garmentRepository: GarmentRepository
) {
    suspend operator fun invoke(
        userId: String,
        uri: Uri,
        name: String,
        category: String
    ): Long = garmentRepository.addGarment(userId, uri, name, category)
}
