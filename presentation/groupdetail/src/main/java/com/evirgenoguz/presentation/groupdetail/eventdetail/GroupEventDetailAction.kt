package com.evirgenoguz.presentation.groupdetail.eventdetail

sealed interface GroupEventDetailAction {

    data class GetGroupEventDetail(val eventId: Int) : GroupEventDetailAction

    data class LeaveGroupEvent(val eventId: Int) : GroupEventDetailAction

    data class JoinGroupEvent(val eventId: Int) : GroupEventDetailAction

    data class ChangeJoinState(val joinState: Boolean): GroupEventDetailAction

}