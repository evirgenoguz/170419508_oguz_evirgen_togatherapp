package com.evirgenoguz.presentation.auth.register

sealed interface RegisterEvent {
    data object RegisterSuccess : RegisterEvent
    data class Error(val errorMessage: String) : RegisterEvent
}