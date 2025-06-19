package com.evirgenoguz.domain.usecase.groupdetail

import com.evirgenoguz.core.domain.util.Result
import com.evirgenoguz.domain.repository.EventRepository
import javax.inject.Inject

class LeaveGroupEventUseCase @Inject constructor(
    private val eventRepository: EventRepository
) {
    suspend operator fun invoke(eventId: Int): Result<Unit> {
        return eventRepository.leaveEvent(eventId)
    }

}