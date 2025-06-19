package com.evirgenoguz.presentation.groupdetail.eventdetail

import com.evirgenoguz.domain.model.groupdetail.GroupEventDomainModel

sealed interface GroupEventDetailEvent {
    data class Error(val message: String) : GroupEventDetailEvent
    data class GroupEventDetailSuccess(val groupEventDetailDomainModel: GroupEventDomainModel) :
        GroupEventDetailEvent

    data object JoinGroupEventSuccess : GroupEventDetailEvent
    data object LeaveGroupEventSuccess : GroupEventDetailEvent
    data object ChangedJoinState : GroupEventDetailEvent

}