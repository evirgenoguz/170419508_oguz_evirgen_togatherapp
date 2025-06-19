package com.evirgenoguz.presentation.groupdetail.creategroupevent

sealed interface CreateGroupEventEvent {
    data class Error(val message: String) : CreateGroupEventEvent
    data object CreateEventSuccess : CreateGroupEventEvent
}