package com.evirgenoguz.domain.usecase.profile

import com.evirgenoguz.core.domain.util.Result
import com.evirgenoguz.domain.model.profile.DistrictLookup
import com.evirgenoguz.domain.repository.ProfileRepository
import javax.inject.Inject

class LookupDistrictsUseCase @Inject constructor(
    private val profileRepository: ProfileRepository
) {
    suspend operator fun invoke(cityCode: String): Result<List<DistrictLookup>> {
        return profileRepository.lookupDistricts(cityCode)
    }
}