package com.evirgenoguz.presentation.foryou

import com.evirgenoguz.domain.model.campaign.CampaignDomainModel

data class ForYouState(
    val campaignList: List<CampaignDomainModel> = emptyList<CampaignDomainModel>()
)
