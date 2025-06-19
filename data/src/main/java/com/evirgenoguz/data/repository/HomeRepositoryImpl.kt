package com.evirgenoguz.data.repository

import com.evirgenoguz.core.domain.util.Result
import com.evirgenoguz.data.datasource.remote.HomeDataSource
import com.evirgenoguz.data.model.request.home.toRequestModel
import com.evirgenoguz.domain.model.GroupDomainModel
import com.evirgenoguz.domain.model.home.CreateGroupDomainModel
import com.evirgenoguz.domain.repository.HomeRepository
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val homeDataSource: HomeDataSource
) : HomeRepository {
    override suspend fun getGroups(): Result<List<GroupDomainModel>> {
        return homeDataSource.getGroups()
    }

    override suspend fun createGroup(createGroupDomainModel: CreateGroupDomainModel): Result<GroupDomainModel> {
        return homeDataSource.createGroup(createGroupDomainModel.toRequestModel())
    }

    override suspend fun joinGroup(inviteCode: String): Result<Unit> {
        return homeDataSource.joinGroup(inviteCode)
    }
}