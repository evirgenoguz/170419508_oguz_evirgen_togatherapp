package com.evirgenoguz.presentation.groupdetail.history

sealed interface GroupDetailHistoryAction {
    data class GetGroupEventsHistoryEvent(val groupId: Int) : GroupDetailHistoryAction

}