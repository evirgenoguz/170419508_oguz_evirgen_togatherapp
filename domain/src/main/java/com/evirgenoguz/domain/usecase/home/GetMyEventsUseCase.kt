package com.evirgenoguz.domain.usecase.home

import com.evirgenoguz.core.domain.util.Result
import com.evirgenoguz.domain.model.groupdetail.GroupEventDomainModel
import com.evirgenoguz.domain.repository.EventRepository
import javax.inject.Inject

class GetMyEventsUseCase @Inject constructor(
    private val eventRepository: EventRepository
) {
    suspend operator fun invoke(): Result<List<GroupEventDomainModel>> {
        return eventRepository.getMyEvents()
    }
}