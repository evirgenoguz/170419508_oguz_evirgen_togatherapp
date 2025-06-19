package com.evirgenoguz.presentation.home.group.join

sealed interface JoinGroupEvent {
    data class JoinGroupError(val message: String) : JoinGroupEvent
    data object JoinGroupSuccess : JoinGroupEvent
}