package com.evirgenoguz.data.model.request.profile

import com.evirgenoguz.domain.usecase.profile.UpdateUserModel
import com.google.gson.annotations.SerializedName

data class UpdateProfileRequestModel(
    @SerializedName("firstName")
    val firstName: String,
    @SerializedName("lastName")
    val lastName: String,
    @SerializedName("username")
    val username: String,
    @SerializedName("birthDate")
    val birthDate: String,
    @SerializedName("bio")
    val bio: String,
    @SerializedName("imageUrl")
    val imageUrl: String,
    @SerializedName("gender")
    val gender: String
)

fun UpdateUserModel.toRequestModel() = UpdateProfileRequestModel(
    firstName = this.firstName,
    lastName = this.lastName,
    username = this.userName,
    birthDate = this.birthDate,
    bio = this.bio,
    imageUrl = this.imageUrl.toString(),
    gender = this.gender
)
