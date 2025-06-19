package com.evirgenoguz.data.model.request.groupdetail

import com.evirgenoguz.domain.model.groupdetail.CreateGroupEventDomainModel
import com.google.gson.annotations.SerializedName

data class CreateEventRequest(
    @SerializedName("name")
    val name: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("startDate")
    val startDate: String,
    @SerializedName("endDate")
    val endDate: String,
    @SerializedName("eventLocationId")
    val eventLocationId: Int?,
    @SerializedName("createdByGroupId")
    val createdByGroupId: Int,
    @SerializedName("eventType")
    val eventType: String,
    @SerializedName("eventCategory")
    val eventCategory: String,
    @SerializedName("totalParticipant")
    val totalParticipant: Int
)

fun CreateGroupEventDomainModel.toRequestModel() = CreateEventRequest(
    name = this.name,
    description = this.description,
    startDate = this.startDate.toString(),
    endDate = this.endDate.toString(),
    eventLocationId = this.eventLocationId,
    createdByGroupId = this.createdByGroupId,
    eventType = "GROUP_EVENT",
    eventCategory = this.eventCategory,
    totalParticipant = this.totalParticipants
)