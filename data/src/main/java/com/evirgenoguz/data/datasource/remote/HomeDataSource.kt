package com.evirgenoguz.data.datasource.remote

import com.evirgenoguz.core.data.networking.safeCall
import com.evirgenoguz.core.domain.util.Result
import com.evirgenoguz.data.model.request.home.CreateGroupRequest
import com.evirgenoguz.data.model.response.home.toDomainModel
import com.evirgenoguz.data.network.HomeApi
import com.evirgenoguz.domain.model.GroupDomainModel

class HomeDataSource(
    private val homeApi: HomeApi
) {
    suspend fun getGroups(): Result<List<GroupDomainModel>> {
        val result = safeCall {
            homeApi.getGroups()
        }
        return when (result) {
            is Result.Error -> Result.Error(result.error)
            is Result.Success -> Result.Success(result.data.toDomainModel())
        }
    }

    suspend fun createGroup(createGroupRequest: CreateGroupRequest): Result<GroupDomainModel> {
        val result = safeCall {
            homeApi.createGroup(createGroupRequest)
        }
        return when (result) {
            is Result.Error -> Result.Error(result.error)
            is Result.Success -> Result.Success(result.data.toDomainModel())
        }
    }

    suspend fun joinGroup(inviteGroup: String): Result<Unit> {
        val result = safeCall {
            homeApi.joinGroup(inviteGroup)
        }
        return when (result) {
            is Result.Error -> Result.Error(result.error)
            is Result.Success -> Result.Success(Unit)
        }
    }
}