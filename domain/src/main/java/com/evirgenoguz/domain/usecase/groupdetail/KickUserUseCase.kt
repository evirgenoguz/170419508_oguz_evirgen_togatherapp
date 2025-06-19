package com.evirgenoguz.domain.usecase.groupdetail

import com.evirgenoguz.core.domain.util.Result
import com.evirgenoguz.domain.repository.GroupRepository
import javax.inject.Inject

class KickUserUseCase @Inject constructor(
    private val groupRepository: GroupRepository
) {
    suspend operator fun invoke(inviteCode: String, username: String): Result<Unit> {
        return groupRepository.kickUser(inviteCode, username)
    }
}