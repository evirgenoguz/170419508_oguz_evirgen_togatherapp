package com.evirgenoguz.domain.repository

import com.evirgenoguz.core.domain.util.Result
import com.evirgenoguz.domain.model.campaign.CampaignDomainModel

interface CampaignRepository {
    suspend fun getCampaigns(districtId: Int): Result<List<CampaignDomainModel>>
}