package com.evirgenoguz.domain.repository

import com.evirgenoguz.core.domain.util.Result
import com.evirgenoguz.domain.model.GroupDomainModel
import com.evirgenoguz.domain.model.home.CreateGroupDomainModel

interface HomeRepository {
    suspend fun getGroups(): Result<List<GroupDomainModel>>
    suspend fun createGroup(createGroupDomainModel: CreateGroupDomainModel): Result<GroupDomainModel>
    suspend fun joinGroup(inviteCode: String): Result<Unit>
}