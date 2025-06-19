package com.evirgenoguz.data.model.request.profile

import com.evirgenoguz.domain.model.profile.ChangePasswordModel
import com.google.gson.annotations.SerializedName

data class ChangePasswordRequest(
    @SerializedName("currentPassword")
    val currentPassword: String,
    @SerializedName("newPassword")
    val newPassword: String,
    @SerializedName("confirmPassword")
    val confirmPassword: String
)

fun ChangePasswordModel.toRequestModel(): ChangePasswordRequest {
    return ChangePasswordRequest(
        currentPassword = oldPassword,
        newPassword = newPassword,
        confirmPassword = newPassword
    )
}