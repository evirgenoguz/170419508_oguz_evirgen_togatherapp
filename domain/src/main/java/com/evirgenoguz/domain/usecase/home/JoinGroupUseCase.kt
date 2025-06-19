package com.evirgenoguz.domain.usecase.home

import com.evirgenoguz.core.domain.util.Result
import com.evirgenoguz.domain.repository.HomeRepository
import javax.inject.Inject

class JoinGroupUseCase @Inject constructor(
    private val homeRepository: HomeRepository
) {
    suspend operator fun invoke(groupId: String): Result<Unit> {
        return homeRepository.joinGroup(groupId)
    }
}