package com.evirgenoguz.domain.repository

import com.evirgenoguz.core.domain.util.Result
import com.evirgenoguz.domain.model.groupdetail.GroupMemberDomainModel

interface GroupRepository {
    suspend fun leaveGroup(inviteCode: String): Result<Unit>
    suspend fun getGroupMembers(inviteCode: String): Result<List<GroupMemberDomainModel>>
    suspend fun kickUser(inviteCode: String, username: String): Result<Unit>
    suspend fun closeGroup(inviteCode: String): Result<Unit>
}