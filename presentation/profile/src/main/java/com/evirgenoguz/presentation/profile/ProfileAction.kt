package com.evirgenoguz.presentation.profile

import com.evirgenoguz.domain.model.profile.ChangePasswordModel

sealed interface ProfileAction {
    object OnLogoutClick : ProfileAction
    data class OnChangePasswordClick(val changePasswordModel: ChangePasswordModel) : ProfileAction
}