package com.evirgenoguz.presentation.home.group.create

sealed interface CreateGroupEvent {
    data class Error(val errorMessage: String) : CreateGroupEvent
    data object CreateGroupSuccess : CreateGroupEvent
}