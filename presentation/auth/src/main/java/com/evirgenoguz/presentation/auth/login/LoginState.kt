package com.evirgenoguz.presentation.auth.login

data class LoginState(
    val email: String = "evirgenoguz@gmail.com",
    val password: String = "Test1234*-+",
    val canLogin: Boolean = false,
)
