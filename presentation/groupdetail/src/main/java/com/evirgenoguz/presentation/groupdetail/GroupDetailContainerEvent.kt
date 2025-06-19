package com.evirgenoguz.presentation.groupdetail

sealed interface GroupDetailContainerEvent {
    data object LeaveGroupSuccess : GroupDetailContainerEvent
}