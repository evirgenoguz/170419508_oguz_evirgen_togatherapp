package com.evirgenoguz.presentation.groupdetail.members

sealed interface GroupDetailMembersEvent {
    data class Error(val errorMessage: String) : GroupDetailMembersEvent
    data object GroupMembersDataSuccess : GroupDetailMembersEvent
    data object CloseGroupSuccess : GroupDetailMembersEvent
    data object KickUserSuccess : GroupDetailMembersEvent
}