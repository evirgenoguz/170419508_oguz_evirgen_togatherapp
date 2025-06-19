package com.evirgenoguz.data.model.request.home

import com.evirgenoguz.domain.model.home.CreateGroupDomainModel
import com.google.gson.annotations.SerializedName

data class CreateGroupRequest(
    @SerializedName("name")
    val name: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("image")
    val image: String
)

fun CreateGroupDomainModel.toRequestModel(): CreateGroupRequest {
    return CreateGroupRequest(
        name = this.name,
        description = this.description,
        image = this.image.orEmpty()
    )
}
