package com.evirgenoguz.data.model.request.groupdetail

import com.evirgenoguz.domain.model.groupdetail.CreateEventLocationReqDomainModel
import com.google.gson.annotations.SerializedName

data class CreateEventLocationRequest(
    @SerializedName("name") var name: String? = null,
    @SerializedName("address") var address: String,
    @SerializedName("latitude") var latitude: Double,
    @SerializedName("longitude") var longitude: Double,
    @SerializedName("cityId") var cityId: Int,
    @SerializedName("districtId") var districtId: Int
)

fun CreateEventLocationReqDomainModel.toRequestModel(): CreateEventLocationRequest {
    return CreateEventLocationRequest(
        name = this.name,
        address = this.address.orEmpty(),
        latitude = this.latitude ?: 0.0,
        longitude = this.longitude ?: 0.0,
        cityId = this.cityId ?: -1,
        districtId = this.districtId ?: -1
    )
}
