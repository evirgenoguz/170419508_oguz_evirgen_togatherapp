package com.evirgenoguz.presentation.auth.login

sealed interface LoginEvent {
    data object LoginSuccess : LoginEvent
    data class LoginError(val errorMessage: String) : LoginEvent
    data class ForgotPasswordSuccess(val email: String): LoginEvent
}