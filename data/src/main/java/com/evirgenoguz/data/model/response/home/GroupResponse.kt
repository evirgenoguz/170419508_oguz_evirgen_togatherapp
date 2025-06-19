package com.evirgenoguz.data.model.response.home

import com.evirgenoguz.domain.model.GroupDomainModel
import com.google.gson.annotations.SerializedName

data class GroupResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("inviteCode")
    val inviteCode: String,
    @SerializedName("pictureKey")
    val pictureKey: String?,
    @SerializedName("createdByUser")
    val createdByUser: String?,
    @SerializedName("createDate")
    val createDate: String
)

fun List<GroupResponse>.toDomainModel(): List<GroupDomainModel> {
    return this.map {
        GroupDomainModel(
            id = it.id,
            name = it.name.orEmpty(),
            description = it.description.orEmpty(),
            inviteCode = it.inviteCode,
            pictureKey = it.pictureKey.orEmpty(),
            createdByUser = it.createdByUser.orEmpty(),
            createDate = it.createDate
        )
    }
}

fun GroupResponse.toDomainModel(): GroupDomainModel {
    return GroupDomainModel(
        id = id,
        name = name.orEmpty(),
        description = description.orEmpty(),
        inviteCode = inviteCode,
        pictureKey = pictureKey.orEmpty(),
        createdByUser = createdByUser.orEmpty(),
        createDate = createDate
    )
}