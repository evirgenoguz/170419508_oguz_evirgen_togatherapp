package com.evirgenoguz.presentation.groupdetail.events

import com.evirgenoguz.domain.model.groupdetail.GroupEventDomainModel

data class GroupDetailState(
    val groupEventList: List<GroupEventDomainModel> = emptyList(),
)
