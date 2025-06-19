package com.evirgenoguz.presentation.auth.register

sealed interface RegisterAction {
    data class OnRegisterClick(
        val email: String,
        val password: String,
        val confirmPassword: String
    ) : RegisterAction
}