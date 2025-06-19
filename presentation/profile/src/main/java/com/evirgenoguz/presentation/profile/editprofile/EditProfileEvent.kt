package com.evirgenoguz.presentation.profile.editprofile

import com.evirgenoguz.domain.model.profile.ProfileUserModel

sealed interface EditProfileEvent {

    data class UpdateProfileError(val message: String) : EditProfileEvent
    data class UpdateProfileSuccess(val profileUserModel: ProfileUserModel) : EditProfileEvent

}