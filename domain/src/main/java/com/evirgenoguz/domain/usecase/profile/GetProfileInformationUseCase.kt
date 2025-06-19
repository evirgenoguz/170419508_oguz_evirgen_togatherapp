package com.evirgenoguz.domain.usecase.profile

import com.evirgenoguz.core.domain.util.Result
import com.evirgenoguz.domain.model.profile.ProfileUserModel
import com.evirgenoguz.domain.repository.ProfileRepository
import javax.inject.Inject

class GetProfileInformationUseCase @Inject constructor(
    private val profileRepository: ProfileRepository
) {
    suspend operator fun invoke(): Result<ProfileUserModel> {
        return profileRepository.getProfileInformation()
    }
}