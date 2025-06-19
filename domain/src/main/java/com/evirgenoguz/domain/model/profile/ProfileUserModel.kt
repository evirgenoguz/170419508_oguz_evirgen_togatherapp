package com.evirgenoguz.domain.model.profile

import java.io.Serializable

data class ProfileUserModel(
    val email: String = "",
    val username: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val fullName: String = "",
    val birthDate: String = "",
    val bio: String = "",
    val gender: String = "",
    val phoneNumber: String = "",
    val city: String = "",
    val country: String = "",
    val profilePictureUrl: String? = null,
    val credibility: Int = 0,
    val profileCompleted: Boolean = false
) : Serializable