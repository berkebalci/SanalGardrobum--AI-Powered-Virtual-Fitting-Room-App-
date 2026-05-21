package com.example.sanalgardrobum.domain.usecase.garment

import com.example.sanalgardrobum.domain.model.Garment
import com.example.sanalgardrobum.domain.repository.GarmentRepository
import javax.inject.Inject

class DeleteGarmentUseCase @Inject constructor(
    private val garmentRepository: GarmentRepository
) {
    suspend operator fun invoke(garment: Garment) =
        garmentRepository.deleteGarment(garment)
}
