package com.evirgenoguz.domain.usecase.groupdetail

import com.evirgenoguz.domain.model.groupdetail.UpdateLiveLocationDomainModel
import com.evirgenoguz.domain.repository.EventRepository
import javax.inject.Inject

class UpdateLiveLocationUseCase @Inject constructor(
    private val eventRepository: EventRepository
) {
    suspend operator fun invoke(updateLiveLocationDomainModel: UpdateLiveLocationDomainModel) =
        eventRepository.updateLocation(updateLiveLocationDomainModel)
}
