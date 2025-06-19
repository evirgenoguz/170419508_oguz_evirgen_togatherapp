package com.evirgenoguz.data.model.response.profile

import com.evirgenoguz.domain.model.profile.ProfileUserModel
import kotlinx.serialization.SerialName

data class ProfileInformationResponse(
    @SerialName("email")
    val email: String,
    @SerialName("username")
    val username: String?,
    @SerialName("firstName")
    val firstName: String?,
    @SerialName("lastName")
    val lastName: String?,
    @SerialName("birthDate")
    val birthDate: String?,
    @SerialName("bio")
    val bio: String?,
    @SerialName("gender")
    val gender: String?,
    @SerialName("phoneNumber")
    val phoneNumber: String?,
    @SerialName("city")
    val city: String?,
    @SerialName("country")
    val country: String?,
    @SerialName("profilePictureUrl")
    val profilePictureUrl: String?,
    @SerialName("credibility")
    val credibility: Int?,
    @SerialName("profileCompleted")
    val profileCompleted: Boolean
)

fun ProfileInformationResponse.toDomainModel(): ProfileUserModel {
    return ProfileUserModel(
        email = email,
        username = username.orEmpty(),
        firstName = firstName.orEmpty(),
        lastName = lastName.orEmpty(),
        fullName = firstName.orEmpty() + " " + lastName.orEmpty(),
        birthDate = birthDate.orEmpty(),
        bio = bio.orEmpty(),
        gender = gender.orEmpty(),
        phoneNumber = phoneNumber.orEmpty(),
        city = city.orEmpty(),
        country = country.orEmpty(),
        profilePictureUrl = profilePictureUrl,
        credibility = credibility ?: 0,
        profileCompleted = profileCompleted
    )
}
