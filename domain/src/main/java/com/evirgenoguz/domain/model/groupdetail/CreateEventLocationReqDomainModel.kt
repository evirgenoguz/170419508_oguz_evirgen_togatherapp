package com.evirgenoguz.domain.model.groupdetail

data class CreateEventLocationReqDomainModel(
    var name: String? = null,
    var address: String? = null,
    var latitude: Double? = null,
    var longitude: Double? = null,
    var cityId: Int? = null,
    var districtId: Int? = null
)
