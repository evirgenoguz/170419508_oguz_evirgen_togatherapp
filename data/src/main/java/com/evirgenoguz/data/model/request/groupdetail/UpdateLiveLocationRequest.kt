package com.evirgenoguz.data.model.request.groupdetail

import com.evirgenoguz.domain.model.groupdetail.UpdateLiveLocationDomainModel
import com.google.gson.annotations.SerializedName

data class UpdateLiveLocationRequest(
    @SerializedName("latitude") var latitude: Double? = null,
    @SerializedName("longitude") var longitude: Double? = null,
    @SerializedName("username") var username: String? = null
)

fun UpdateLiveLocationDomainModel.toRequestModel() = UpdateLiveLocationRequest(
    latitude = latitude,
    longitude = longitude,
    username = username
)

fun List<UpdateLiveLocationRequest>.toDomainModel(): List<UpdateLiveLocationDomainModel> {
    return this.map {
        UpdateLiveLocationDomainModel(
            latitude = it.latitude ?: 0.0,
            longitude = it.longitude ?: 0.0,
            username = it.username ?: ""
        )
    }
}