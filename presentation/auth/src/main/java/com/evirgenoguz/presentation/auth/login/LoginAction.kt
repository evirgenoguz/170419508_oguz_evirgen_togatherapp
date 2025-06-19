package com.evirgenoguz.presentation.auth.login

sealed interface LoginAction {
    data class OnLoginClick(
        val email: String,
        val password: String
    ) : LoginAction

    data class ForgotPassword(val email: String) : LoginAction
}