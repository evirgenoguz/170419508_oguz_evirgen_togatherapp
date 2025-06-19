package com.evirgenoguz.data.model.request

import com.google.gson.annotations.SerializedName

data class RegisterRequestModel(
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("confirmPassword")
    val confirmPassword: String
)