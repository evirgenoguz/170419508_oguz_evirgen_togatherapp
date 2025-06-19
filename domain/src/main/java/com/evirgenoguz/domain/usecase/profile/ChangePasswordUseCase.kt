package com.evirgenoguz.domain.usecase.profile

import com.evirgenoguz.core.domain.util.Result
import com.evirgenoguz.domain.model.profile.ChangePasswordModel
import com.evirgenoguz.domain.repository.ProfileRepository
import javax.inject.Inject

class ChangePasswordUseCase @Inject constructor(
    private val profileRepository: ProfileRepository
) {
    suspend operator fun invoke(changePasswordModel: ChangePasswordModel): Result<Unit> {
        return profileRepository.changePassword(changePasswordModel)
    }
}