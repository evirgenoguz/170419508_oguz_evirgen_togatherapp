package com.evirgenoguz.domain.model.auth

data class AuthModel(
    val accessToken: String,
    val refreshToken: String,
    val user: User,
    val expiresIn: Long
)