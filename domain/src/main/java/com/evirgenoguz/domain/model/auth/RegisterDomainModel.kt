package com.evirgenoguz.domain.model.auth

data class RegisterDomainModel(
    val email: String,
    val username: String,
    val emailVerified: Boolean
)