package com.evirgenoguz.domain.usecase.groupdetail

import com.evirgenoguz.core.domain.util.Result
import com.evirgenoguz.domain.model.groupdetail.GroupMemberDomainModel
import com.evirgenoguz.domain.repository.GroupRepository
import javax.inject.Inject

class GetGroupMembersUseCase @Inject constructor(
    private val groupRepository: GroupRepository
) {
    suspend operator fun invoke(inviteCode: String): Result<List<GroupMemberDomainModel>> {
        return groupRepository.getGroupMembers(inviteCode)
    }
}