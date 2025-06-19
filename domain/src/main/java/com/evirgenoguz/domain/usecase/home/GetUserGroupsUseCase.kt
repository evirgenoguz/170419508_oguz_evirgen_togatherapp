package com.evirgenoguz.domain.usecase.home

import com.evirgenoguz.core.domain.util.Result
import com.evirgenoguz.domain.model.GroupDomainModel
import com.evirgenoguz.domain.repository.HomeRepository
import javax.inject.Inject

class GetUserGroupsUseCase @Inject constructor(
    private val homeRepository: HomeRepository
) {
    suspend operator fun invoke(): Result<List<GroupDomainModel>> {
        return homeRepository.getGroups()
    }
}