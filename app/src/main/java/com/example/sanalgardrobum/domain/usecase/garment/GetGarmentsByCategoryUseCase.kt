package com.example.sanalgardrobum.domain.usecase.garment

import com.example.sanalgardrobum.domain.model.Garment
import com.example.sanalgardrobum.domain.repository.GarmentRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetGarmentsByCategoryUseCase @Inject constructor(
    private val garmentRepository: GarmentRepository
) {
    operator fun invoke(userId: String, category: String): Flow<List<Garment>> =
        garmentRepository.getGarmentsByCategory(userId, category)
}
