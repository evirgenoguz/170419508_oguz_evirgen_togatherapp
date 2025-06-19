package com.evirgenoguz.data.model.response.groupdetail

import com.evirgenoguz.domain.model.groupdetail.GroupMemberDomainModel
import com.google.gson.annotations.SerializedName

data class GroupMemberResponse(
    @SerializedName("username")
    val username: String,
    @SerializedName("userImage")
    val userImage: String,
    @SerializedName("role")
    val role: String
)

fun List<GroupMemberResponse>.toDomainModel(): List<GroupMemberDomainModel> {
    return map {
        GroupMemberDomainModel(
            username = it.username,
            userImage = it.userImage,
            role = it.role
        )
    }
}
