package com.evirgenoguz.presentation.home.group.create

sealed interface CreateGroupAction {
    data class OnCreateGroupClicked(
        val name: String,
        val description: String
    ) : CreateGroupAction
}