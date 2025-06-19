package com.evirgenoguz.data.model.response

import com.evirgenoguz.domain.model.auth.RegisterDomainModel
import com.google.gson.annotations.SerializedName

data class RegisterResponse(
    @SerializedName("email")
    val email: String,
    @SerializedName("username")
    val username: String?,
    @SerializedName("emailVerified")
    val emailVerified: Boolean,
)

fun RegisterResponse.toDomainModel(): RegisterDomainModel {
    return RegisterDomainModel(
        email = email,
        username = username.orEmpty(),
        emailVerified = emailVerified
    )
}
