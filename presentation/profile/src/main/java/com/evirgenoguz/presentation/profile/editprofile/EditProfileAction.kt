package com.evirgenoguz.presentation.profile.editprofile

import com.evirgenoguz.domain.usecase.profile.UpdateUserModel
import java.io.InputStream

sealed interface EditProfileAction {
    data class OnUpdateProfileClick(val updateUserModel: UpdateUserModel) : EditProfileAction
    data class OnAvatarSelected(val inputStream: InputStream) : EditProfileAction
}