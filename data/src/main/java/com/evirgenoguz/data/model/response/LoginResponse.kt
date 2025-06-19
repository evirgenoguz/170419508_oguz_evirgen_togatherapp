package com.evirgenoguz.data.model.response

import com.evirgenoguz.domain.model.auth.AuthModel
import com.evirgenoguz.domain.model.auth.User
import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("accessToken")
    val accessToken: String,
    @SerializedName("refreshToken")
    val refreshToken: String,
    @SerializedName("user")
    val user: LoginUser,
    @SerializedName("expiresIn")
    val expiresIn: Long
)

data class LoginUser(
    @SerializedName("email")
    val email: String,
    @SerializedName("username")
    val username: String?,
    @SerializedName("emailVerified")
    val emailVerified: Boolean
)

fun LoginUser.toUserModel(): User {
    return User(
        email = email,
        username = username.orEmpty(),
        emailVerified = emailVerified
    )
}

fun LoginResponse.toAuthModel(): AuthModel {
    return AuthModel(
        accessToken = accessToken,
        refreshToken = refreshToken,
        user = user.toUserModel(),
        expiresIn = expiresIn
    )
}

