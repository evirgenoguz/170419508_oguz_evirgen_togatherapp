package com.evirgenoguz.domain.usecase.profile

import com.evirgenoguz.core.domain.util.Result
import com.evirgenoguz.domain.model.profile.CountryLookup
import com.evirgenoguz.domain.repository.ProfileRepository
import javax.inject.Inject

class LookupCountriesUseCase @Inject constructor(
    private val profileRepository: ProfileRepository
) {
    suspend operator fun invoke(): Result<List<CountryLookup>> {
        return profileRepository.lookupCountries()
    }
}