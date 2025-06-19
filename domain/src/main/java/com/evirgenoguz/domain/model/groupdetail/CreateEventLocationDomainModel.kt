package com.evirgenoguz.domain.model.groupdetail

data class CreateEventLocationDomainModel(
    var id: Int? = null,
    var name: String? = null,
    var address: String? = null,
    var latitude: Double,
    var longitude: Double,
    var city: CityDomainModel? = CityDomainModel(),
    var district: DistrictDomainModel? = DistrictDomainModel()
)
