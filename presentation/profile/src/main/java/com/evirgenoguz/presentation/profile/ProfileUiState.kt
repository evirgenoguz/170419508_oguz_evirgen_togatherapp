package com.evirgenoguz.presentation.profile

import com.evirgenoguz.domain.model.profile.ProfileUserModel

data class ProfileUiState(
    val userModel: ProfileUserModel = ProfileUserModel(),
)