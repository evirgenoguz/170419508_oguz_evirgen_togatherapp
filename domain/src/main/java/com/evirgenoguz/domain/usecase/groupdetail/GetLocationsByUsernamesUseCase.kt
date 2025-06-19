package com.evirgenoguz.domain.usecase.groupdetail

import com.evirgenoguz.domain.repository.EventRepository
import javax.inject.Inject

class GetLocationsByUsernamesUseCase @Inject constructor(
    private val eventRepository: EventRepository
) {
    suspend operator fun invoke(usernames: List<String>) = eventRepository.getLocationsByUsernames(usernames)
}