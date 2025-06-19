package com.evirgenoguz.presentation.groupdetail.history

import com.evirgenoguz.domain.model.groupdetail.GroupEventDomainModel

data class GroupDetailHistoryState(
    val groupEventDomainModelList: List<GroupEventDomainModel> = emptyList<GroupEventDomainModel>()
)