package com.evirgenoguz.data.model.response.groupdetail

import com.evirgenoguz.domain.model.groupdetail.UpdateLiveLocationDomainModel
import com.google.gson.annotations.SerializedName

data class LiveLocationResponse(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("latitude") var latitude: Double? = null,
    @SerializedName("longitude") var longitude: Double? = null,
    @SerializedName("lastUpdated") var lastUpdated: String? = null,
    @SerializedName("username") var username: String? = null
)

fun List<LiveLocationResponse>.toDomainModel(): List<UpdateLiveLocationDomainModel> {
    return this.map {
        UpdateLiveLocationDomainModel(
            latitude = it.latitude ?: 0.0,
            longitude = it.longitude ?: 0.0,
            username = it.username ?: ""
        )
    }
}
