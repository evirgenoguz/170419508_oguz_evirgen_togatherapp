package com.evirgenoguz.domain.usecase.profile

import com.evirgenoguz.core.domain.util.Result
import com.evirgenoguz.domain.model.profile.CityLookup
import com.evirgenoguz.domain.repository.ProfileRepository
import javax.inject.Inject

class LookupCitiesUseCase @Inject constructor(
    private val profileRepository: ProfileRepository
) {
    suspend operator fun invoke(code: String): Result<List<CityLookup>> {
        return profileRepository.lookupCities(code)
    }
}