package com.evirgenoguz.presentation.profile

import androidx.annotation.StringRes

data class ProfileActionItem(
    @StringRes val titleRes: Int,
    val onClick: () -> Unit
)
