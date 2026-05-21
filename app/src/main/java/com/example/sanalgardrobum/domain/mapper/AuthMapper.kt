package com.example.sanalgardrobum.domain.mapper

import com.google.firebase.auth.FirebaseUser
import com.example.sanalgardrobum.domain.model.User

fun FirebaseUser.toDomain(): User = User(
    uid = uid,
    displayName = displayName,
    email = email,
    photoUrl = photoUrl?.toString()
)
