package com.evirgenoguz.data.datasource.remote

import com.evirgenoguz.core.data.networking.safeCall
import com.evirgenoguz.core.domain.util.Result
import com.evirgenoguz.data.model.response.toDomainModel
import com.evirgenoguz.data.network.CampaignApi
import com.evirgenoguz.domain.model.campaign.CampaignDomainModel

class CampaignRemoteDataSource(
    private val campaignApi: CampaignApi
) {
    suspend fun getCampaigns(districtId: Int): Result<List<CampaignDomainModel>> {
        val result = safeCall {
            campaignApi.getCampaigns(districtId)
        }

        return when (result) {
            is Result.Error -> Result.Error(result.error)
            is Result.Success -> Result.Success(result.data.toDomainModel())
        }
    }
}