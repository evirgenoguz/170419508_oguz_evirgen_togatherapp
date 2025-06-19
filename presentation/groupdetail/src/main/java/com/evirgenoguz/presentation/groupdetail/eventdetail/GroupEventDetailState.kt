package com.evirgenoguz.presentation.groupdetail.eventdetail

import com.evirgenoguz.domain.model.groupdetail.GroupEventDomainModel

data class GroupEventDetailState(
    val id: Int = 0,
    val groupEventDomainModel: GroupEventDomainModel? = null,
    var alreadyJoined: Boolean = false
)
