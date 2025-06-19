package com.evirgenoguz.presentation.groupdetail.events

sealed interface GroupDetailEventsEvent {
    data class Error(val message: String) : GroupDetailEventsEvent
    data object GroupEventsSuccess : GroupDetailEventsEvent
}