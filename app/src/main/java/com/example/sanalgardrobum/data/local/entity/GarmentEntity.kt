package com.example.sanalgardrobum.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "garments")
data class GarmentEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val userId: String,
    val category: String,
    val imagePath: String,
    val name: String,
    val createdAt: Long = System.currentTimeMillis()
)
