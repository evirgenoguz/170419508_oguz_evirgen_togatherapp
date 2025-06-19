package com.evirgenoguz.domain.usecase.groupdetail

import com.evirgenoguz.core.domain.util.Result
import com.evirgenoguz.domain.model.groupdetail.CreateEventLocationReqDomainModel
import com.evirgenoguz.domain.model.groupdetail.CreateGroupEventDomainModel
import com.evirgenoguz.domain.model.groupdetail.GroupEventDomainModel
import com.evirgenoguz.domain.repository.EventRepository
import javax.inject.Inject

class CreateGroupEventUseCase @Inject constructor(
    private val eventRepository: EventRepository
) {
    suspend operator fun invoke(
        createGroupEventDomainModel: CreateGroupEventDomainModel,
        createGroupEventLocationReqDomainModel: CreateEventLocationReqDomainModel
    ): Result<GroupEventDomainModel> {
        val createEventLocationReqResult =
            eventRepository.createEventLocation(createGroupEventLocationReqDomainModel)

        return when (createEventLocationReqResult) {
            is Result.Error -> createEventLocationReqResult
            is Result.Success -> {
                eventRepository.createGroupEvent(
                    createGroupEventDomainModel.copy(
                        eventLocationId = createEventLocationReqResult.data.id
                    )
                )
            }
        }
    }
}