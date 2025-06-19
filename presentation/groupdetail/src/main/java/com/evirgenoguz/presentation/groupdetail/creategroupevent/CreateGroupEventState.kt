package com.evirgenoguz.presentation.groupdetail.creategroupevent

import com.evirgenoguz.location.LocationModel

data class CreateGroupEventState(
    val fullAddress: String? = null,
    val cityName: String? = null,
    val districtName: String? = null,
    val cityId: Int? = null,
    val districtId: Int? = null,
    val locationModel: LocationModel = LocationModel(0.0, 0.0),
    val startDate: String? = null,
    val endDate: String? = null,
    val starTime: String? = null,
    val endTime: String? = null,
)
