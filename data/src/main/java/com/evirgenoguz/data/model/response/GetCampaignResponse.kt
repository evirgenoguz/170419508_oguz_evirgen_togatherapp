package com.evirgenoguz.data.model.response

import com.evirgenoguz.data.model.response.groupdetail.City
import com.evirgenoguz.data.model.response.groupdetail.toDomainModel
import com.evirgenoguz.domain.model.campaign.CampaignDomainModel
import com.google.gson.annotations.SerializedName

data class GetCampaignResponse(
    @SerializedName("id") var id: Int,
    @SerializedName("campaignCompany") var campaignCompany: String? = null,
    @SerializedName("title") var title: String? = null,
    @SerializedName("description") var description: String? = null,
    @SerializedName("city") var city: City? = City(),
    @SerializedName("latitude") var latitude: Double? = null,
    @SerializedName("longitude") var longitude: Double? = null,
    @SerializedName("endDate") var endDate: String? = null
)

fun List<GetCampaignResponse>.toDomainModel(): List<CampaignDomainModel> {
    return this.map {
        CampaignDomainModel(
            id = it.id,
            campaignCompany = it.campaignCompany,
            title = it.title,
            description = it.description,
            city = it.city?.toDomainModel(),
            latitude = it.latitude,
            longitude = it.longitude,
            endDate = it.endDate
        )
    }
}
