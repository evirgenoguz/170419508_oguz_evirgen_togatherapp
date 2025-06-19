package com.evirgenoguz.presentation.auth.register

data class RegisterUiState(
    val email: String = "evirgenoguz@gmail.com",
    val password: String = "Test1234*-+",
    val confirmPassword: String = "Test1234*-+",
)
