package com.evirgenoguz.presentation.profile.changelocation

import com.evirgenoguz.domain.model.profile.CityLookup
import com.evirgenoguz.domain.model.profile.CountryLookup
import com.evirgenoguz.domain.model.profile.DistrictLookup

data class ChangeLocationUiState(
    val countryList: List<CountryLookup> = emptyList(),
    val cityList: List<CityLookup> = emptyList(),
    val districtList: List<DistrictLookup> = emptyList(),
    val userLocation: UserLocation = UserLocation()
)

data class UserLocation(
    val countryCode: String = "",
    val cityCode: String = ""
)