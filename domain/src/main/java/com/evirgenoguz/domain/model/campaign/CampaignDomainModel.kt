package com.evirgenoguz.domain.model.campaign

import com.evirgenoguz.domain.model.groupdetail.CityDomainModel

data class CampaignDomainModel(
    var id: Int,
    var campaignCompany: String? = null,
    var title: String? = null,
    var description: String? = null,
    var city: CityDomainModel? = CityDomainModel(),
    var latitude: Double? = null,
    var longitude: Double? = null,
    var endDate: String? = null
)
