package com.evirgenoguz.presentation.profile

sealed interface ProfileEvent {
    data class Error(val errorMessage: String) : ProfileEvent
    data object DataSuccess : ProfileEvent
    data object LogoutSuccess : ProfileEvent
    data class ChangePasswordError(val message: String) : ProfileEvent
    data object ChangePasswordSuccess : ProfileEvent
}