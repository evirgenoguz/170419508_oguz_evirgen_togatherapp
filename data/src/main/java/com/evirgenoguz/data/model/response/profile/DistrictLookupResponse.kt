package com.evirgenoguz.data.model.response.profile

import com.evirgenoguz.domain.model.profile.DistrictLookup
import kotlinx.serialization.SerialName

data class DistrictLookupResponse(
    @SerialName("code")
    val code: String,
    @SerialName("name")
    val name: String
)

fun List<DistrictLookupResponse>.toDomainModel(): List<DistrictLookup> {
    return this.map {
        DistrictLookup(
            code = it.code,
            name = it.name
        )
    }
}