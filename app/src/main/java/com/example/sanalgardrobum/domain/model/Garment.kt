package com.example.sanalgardrobum.domain.model

data class Garment(
    val id: Long = 0,
    val userId: String,
    val category: String,
    val imagePath: String,
    val name: String,
    val createdAt: Long
)
