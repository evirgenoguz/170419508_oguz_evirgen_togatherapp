package com.evirgenoguz.data.model.response.groupdetail

import com.google.gson.annotations.SerializedName

data class GetCityIdResponse(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("code") var code: String? = null,
    @SerializedName("countryId") var countryId: Int? = null
)
