package com.evirgenoguz.presentation.profile.editprofile

import com.evirgenoguz.domain.model.profile.ChangePasswordModel
import java.io.InputStream

data class EditProfileUiState(
    var changePassword: ChangePasswordModel? = null,
    var selectedAvatarInputStream: InputStream? = null,
    var firebaseUrlForAvatar: String? = null
)