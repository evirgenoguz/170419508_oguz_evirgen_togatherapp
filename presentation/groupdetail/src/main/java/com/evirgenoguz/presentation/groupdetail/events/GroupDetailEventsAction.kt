package com.evirgenoguz.presentation.groupdetail.events

sealed interface GroupDetailEventsAction {
    data class GetGroupEvents(val groupId: Int) : GroupDetailEventsAction
}