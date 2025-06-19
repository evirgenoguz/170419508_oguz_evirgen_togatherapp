package com.evirgenoguz.data.model.response.profile

import com.evirgenoguz.domain.model.profile.CityLookup
import kotlinx.serialization.SerialName

data class CityLookupResponse(
    @SerialName("code")
    val code: String,
    @SerialName("name")
    val name: String
)

fun List<CityLookupResponse>.toDomainModel(): List<CityLookup> {
    return this.map {
        CityLookup(
            code = it.code,
            name = it.name
        )
    }
}
