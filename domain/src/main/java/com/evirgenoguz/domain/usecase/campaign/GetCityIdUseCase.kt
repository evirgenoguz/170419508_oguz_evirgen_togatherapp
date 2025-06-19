package com.evirgenoguz.domain.usecase.campaign

import com.evirgenoguz.core.domain.util.Result
import com.evirgenoguz.domain.repository.EventRepository
import javax.inject.Inject

class GetCityIdUseCase @Inject constructor(
    private val eventRepository: EventRepository
) {
    suspend operator fun invoke(cityName: String): Result<Int> {
        return eventRepository.getCityIdByName(cityName)
    }
}