package com.evirgenoguz.presentation.groupdetail

interface GroupDetailContainerAction {
    data class OnLeaveGroup(val inviteCode: String) : GroupDetailContainerAction
}