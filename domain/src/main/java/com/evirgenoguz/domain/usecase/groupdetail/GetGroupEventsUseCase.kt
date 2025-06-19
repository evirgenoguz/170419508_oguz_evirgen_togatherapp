package com.evirgenoguz.domain.usecase.groupdetail

import com.evirgenoguz.core.domain.util.Result
import com.evirgenoguz.domain.model.groupdetail.GroupEventDomainModel
import com.evirgenoguz.domain.repository.EventRepository
import javax.inject.Inject

class GetGroupEventsUseCase @Inject constructor(
    private val eventRepository: EventRepository
) {
    suspend operator fun invoke(groupId: Int): Result<List<GroupEventDomainModel>> {
        return eventRepository.getGroupEvents(groupId)
    }

}