package com.evirgenoguz.presentation.groupdetail.history

sealed interface GroupDetailHistoryEvent {
    data class Error(val message: String) : GroupDetailHistoryEvent
    data object GroupEventsHistorySuccess : GroupDetailHistoryEvent
}