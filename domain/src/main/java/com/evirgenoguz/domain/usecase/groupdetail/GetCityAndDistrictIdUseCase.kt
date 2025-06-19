package com.evirgenoguz.domain.usecase.groupdetail

import com.evirgenoguz.core.domain.util.Result
import com.evirgenoguz.domain.repository.EventRepository
import javax.inject.Inject

class GetCityAndDistrictIdUseCase @Inject constructor(
    private val eventRepository: EventRepository
) {
    suspend operator fun invoke(cityName: String, districtName: String): Result<Pair<Int, Int>> {
        var cityIdAndDistrictId = Pair<Int, Int>(-1, -1)
        val result = eventRepository.getCityIdByName(cityName)
        return when (result) {
            is Result.Error -> Result.Error(result.error)
            is Result.Success -> {
                cityIdAndDistrictId = Pair(result.data, -1)
                val result = eventRepository.getDistrictIdByName(districtName, result.data)
                when (result) {
                    is Result.Error -> Result.Error(result.error)
                    is Result.Success -> {
                        cityIdAndDistrictId = Pair(cityIdAndDistrictId.first, result.data)
                        Result.Success(cityIdAndDistrictId)
                    }
                }
            }
        }
    }
}