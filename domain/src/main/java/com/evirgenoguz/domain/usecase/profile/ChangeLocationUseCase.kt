package com.evirgenoguz.domain.usecase.profile

import com.evirgenoguz.core.domain.util.Result
import com.evirgenoguz.domain.model.profile.ChangeLocationModel
import com.evirgenoguz.domain.model.profile.ProfileUserModel
import com.evirgenoguz.domain.repository.ProfileRepository
import javax.inject.Inject

class ChangeLocationUseCase @Inject constructor(
    private val profileRepository: ProfileRepository
) {
    suspend operator fun invoke(changeLocationModel: ChangeLocationModel): Result<ProfileUserModel> {
        return profileRepository.updateLocation(changeLocationModel)
    }
}