package com.evirgenoguz.presentation.groupdetail.members

import com.evirgenoguz.domain.model.groupdetail.GroupMemberDomainModel

data class GroupDetailMemberState(
    val members: List<GroupMemberDomainModel> = emptyList<GroupMemberDomainModel>()
)
