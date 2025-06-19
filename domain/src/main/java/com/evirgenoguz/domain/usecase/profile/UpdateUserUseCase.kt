package com.evirgenoguz.domain.usecase.profile

import com.evirgenoguz.core.domain.util.Result
import com.evirgenoguz.domain.model.profile.ProfileUserModel
import com.evirgenoguz.domain.repository.ImageRepository
import com.evirgenoguz.domain.repository.ProfileRepository
import java.io.InputStream
import javax.inject.Inject

class UpdateUserUseCase @Inject constructor(
    private val profileRepository: ProfileRepository,
    private val imageRepository: ImageRepository
) {
    suspend operator fun invoke(updateUserModel: UpdateUserModel): Result<ProfileUserModel> {
        val result = if (updateUserModel.inputStream != null && updateUserModel.imageFilename != null){
            imageRepository.uploadAvatarImage(updateUserModel.inputStream, updateUserModel.imageFilename)
        } else {
            return profileRepository.updateUserProfile(updateUserModel)
        }
        return when (result) {
            is Result.Error -> Result.Error(result.error)
            is Result.Success -> profileRepository.updateUserProfile(updateUserModel.copy(imageUrl = result.data))
        }
    }
}

data class UpdateUserModel(
    val firstName: String,
    val lastName: String,
    val userName: String,
    val bio: String,
    val birthDate: String,
    val imageUrl: String? = null,
    val inputStream: InputStream? = null,
    val imageFilename: String? = null,
    val gender: String
)