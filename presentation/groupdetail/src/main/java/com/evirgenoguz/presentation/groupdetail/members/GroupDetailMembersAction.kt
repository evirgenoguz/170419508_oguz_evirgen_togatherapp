package com.evirgenoguz.presentation.groupdetail.members

sealed interface GroupDetailMembersAction {
    data class GetGroupMembers(val inviteCode: String) : GroupDetailMembersAction
    data class OnKickUserClicked(val inviteCode: String, val username: String) : GroupDetailMembersAction
    data class OnDeleteGroupClicked(val inviteCode: String) : GroupDetailMembersAction
}