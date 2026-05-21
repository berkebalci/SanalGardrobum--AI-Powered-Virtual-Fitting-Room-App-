package com.example.sanalgardrobum.domain.mapper

import com.example.sanalgardrobum.data.local.entity.GarmentEntity
import com.example.sanalgardrobum.domain.model.Garment

fun GarmentEntity.toDomain(): Garment = Garment(
    id = id,
    userId = userId,
    category = category,
    imagePath = imagePath,
    name = name,
    createdAt = createdAt
)

fun Garment.toEntity(): GarmentEntity = GarmentEntity(
    id = id,
    userId = userId,
    category = category,
    imagePath = imagePath,
    name = name,
    createdAt = createdAt
)
