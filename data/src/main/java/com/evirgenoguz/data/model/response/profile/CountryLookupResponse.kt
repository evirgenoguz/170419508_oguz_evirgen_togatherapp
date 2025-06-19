package com.evirgenoguz.data.model.response.profile

import com.evirgenoguz.domain.model.profile.CountryLookup
import kotlinx.serialization.SerialName

data class CountryLookupResponse(
    @SerialName("code")
    val code: String,
    @SerialName("name")
    val name: String
)

fun List<CountryLookupResponse>.toDomainModel(): List<CountryLookup> {
    return this.map {
        CountryLookup(
            code = it.code,
            name = it.name
        )
    }
}