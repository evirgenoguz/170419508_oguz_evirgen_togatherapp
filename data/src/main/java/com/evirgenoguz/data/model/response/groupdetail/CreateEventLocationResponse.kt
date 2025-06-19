package com.evirgenoguz.data.model.response.groupdetail

import com.evirgenoguz.domain.model.groupdetail.CreateEventLocationDomainModel
import com.google.gson.annotations.SerializedName

data class CreateEventLocationResponse(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("address") var address: String? = null,
    @SerializedName("latitude") var latitude: Double,
    @SerializedName("longitude") var longitude: Double,
    @SerializedName("city") var city: City? = City(),
    @SerializedName("district") var district: District? = District()
)

fun CreateEventLocationResponse.toDomainModel(): CreateEventLocationDomainModel {
    return CreateEventLocationDomainModel(
        id = this.id,
        name = this.name,
        address = this.address,
        latitude = this.latitude,
        longitude = this.longitude,
        city = this.city?.toDomainModel(),
        district = this.district?.toDomainModel()
    )
}
