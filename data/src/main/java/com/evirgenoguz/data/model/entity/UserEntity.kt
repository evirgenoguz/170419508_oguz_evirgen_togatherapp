package com.evirgenoguz.data.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.evirgenoguz.domain.model.profile.ProfileUserModel

@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey val username: String = "",
    val email: String = "",
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
    val profileCompleted: Boolean = false,
    val cachedAt: Long
)

fun UserEntity.toDomainModel(): ProfileUserModel {
    return ProfileUserModel(
        email = this.email,
        username = this.username,
        firstName = this.firstName,
        lastName = this.lastName,
        fullName = this.fullName,
        birthDate = this.birthDate,
        bio = this.bio,
        gender = this.gender,
        phoneNumber = this.phoneNumber,
        city = this.city,
        country = this.country,
        profilePictureUrl = this.profilePictureUrl,
        credibility = this.credibility,
        profileCompleted = this.profileCompleted
    )
}

fun ProfileUserModel.toEntity(): UserEntity {
    return UserEntity(
        email = this.email,
        username = this.username,
        firstName = this.firstName,
        lastName = this.lastName,
        fullName = this.fullName,
        birthDate = this.birthDate,
        bio = this.bio,
        gender = this.gender,
        phoneNumber = this.phoneNumber,
        city = this.city,
        country = this.country,
        profilePictureUrl = this.profilePictureUrl,
        credibility = this.credibility,
        profileCompleted = this.profileCompleted,
        cachedAt = System.currentTimeMillis()
    )
}