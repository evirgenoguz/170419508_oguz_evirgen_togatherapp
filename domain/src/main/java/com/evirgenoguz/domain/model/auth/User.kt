package com.evirgenoguz.domain.model.auth

data class User(
    val email: String = "",
    val username: String = "",
    val emailVerified: Boolean = false
)
