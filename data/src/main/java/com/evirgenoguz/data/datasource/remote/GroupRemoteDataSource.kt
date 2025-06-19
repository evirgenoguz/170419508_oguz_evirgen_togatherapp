package com.evirgenoguz.data.datasource.remote

import com.evirgenoguz.core.data.networking.safeCall
import com.evirgenoguz.core.domain.util.Result
import com.evirgenoguz.data.model.response.groupdetail.toDomainModel
import com.evirgenoguz.data.network.GroupApi
import com.evirgenoguz.domain.model.groupdetail.GroupMemberDomainModel

class GroupRemoteDataSource(
    private val groupApi: GroupApi
) {

    suspend fun leaveGroup(inviteCode: String): Result<Unit> {
        val result = safeCall {
            groupApi.leaveGroup(inviteCode)
        }
        return when (result) {
            is Result.Error -> Result.Error(result.error)
            is Result.Success -> Result.Success(result.data)
        }
    }

    suspend fun getGroupMembers(inviteCode: String): Result<List<GroupMemberDomainModel>> {
        val result = safeCall {
            groupApi.getGroupMembers(inviteCode)
        }
        return when (result) {
            is Result.Error -> Result.Error(result.error)
            is Result.Success -> Result.Success(result.data.toDomainModel())
        }
    }

    suspend fun kickUser(inviteCode: String, username: String): Result<Unit> {
        val result = safeCall {
            groupApi.kickUserFromGroup(inviteCode, username)
        }

        return when (result) {
            is Result.Error -> Result.Error(result.error)
            is Result.Success -> Result.Success(result.data)
        }
    }

    suspend fun closeGroup(inviteCode: String): Result<Unit> {
        val result = safeCall {
            groupApi.closeGroup(inviteCode)
        }

        return when (result) {
            is Result.Error -> Result.Error(result.error)
            is Result.Success -> Result.Success(result.data)
        }
    }
}