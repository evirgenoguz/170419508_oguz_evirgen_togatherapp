package com.evirgenoguz.data.model.response

import com.google.gson.annotations.SerializedName


data class SampleResponse(
    @SerializedName("sampleData")
    val sampleData: String
)