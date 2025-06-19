package com.evirgenoguz.presentation.home.group.join

sealed interface JoinGroupAction {
    data class OnJoinGroupClick(val inviteCode: String) : JoinGroupAction
}