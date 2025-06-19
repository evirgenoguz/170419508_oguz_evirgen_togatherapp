package com.evirgenoguz.data.repository

import com.evirgenoguz.core.domain.util.Result
import com.evirgenoguz.data.datasource.remote.GroupRemoteDataSource
import com.evirgenoguz.domain.model.groupdetail.GroupMemberDomainModel
import com.evirgenoguz.domain.repository.GroupRepository
import javax.inject.Inject

class GroupRepositoryImpl @Inject constructor(
    private val groupRemoteDataSource: GroupRemoteDataSource
) : GroupRepository {

    override suspend fun leaveGroup(inviteCode: String): Result<Unit> {
        return groupRemoteDataSource.leaveGroup(inviteCode)
    }

    override suspend fun getGroupMembers(inviteCode: String): Result<List<GroupMemberDomainModel>> {
        return groupRemoteDataSource.getGroupMembers(inviteCode)
    }

    override suspend fun kickUser(
        inviteCode: String,
        username: String
    ): Result<Unit> {
        return groupRemoteDataSource.kickUser(inviteCode, username)
    }

    override suspend fun closeGroup(inviteCode: String): Result<Unit> {
        return groupRemoteDataSource.closeGroup(inviteCode)
    }
}