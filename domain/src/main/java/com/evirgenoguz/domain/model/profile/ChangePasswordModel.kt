package com.evirgenoguz.domain.model.profile

data class ChangePasswordModel(
    val oldPassword: String,
    val newPassword: String
)
