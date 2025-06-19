package com.evirgenoguz.data.model.request.profile

import com.evirgenoguz.domain.model.profile.ChangeLocationModel
import com.google.gson.annotations.SerializedName

data class UpdateLocationRequest(
    @SerializedName("cityCode")
    val cityCode: String,
    @SerializedName("countryCode")
    val countryCode: String
)

fun ChangeLocationModel.toRequestModel(): UpdateLocationRequest {
    return UpdateLocationRequest(
        cityCode = this.cityCode,
        countryCode = this.countryCode
    )
}
