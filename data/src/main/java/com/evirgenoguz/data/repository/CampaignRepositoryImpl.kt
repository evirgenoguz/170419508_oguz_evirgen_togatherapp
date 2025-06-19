package com.evirgenoguz.data.repository

import com.evirgenoguz.core.domain.util.Result
import com.evirgenoguz.data.datasource.remote.CampaignRemoteDataSource
import com.evirgenoguz.domain.model.campaign.CampaignDomainModel
import com.evirgenoguz.domain.repository.CampaignRepository
import javax.inject.Inject

class CampaignRepositoryImpl @Inject constructor(
    private val campaignRemoteDataSource: CampaignRemoteDataSource
) : CampaignRepository {
    override suspend fun getCampaigns(districtId: Int): Result<List<CampaignDomainModel>> {
        return campaignRemoteDataSource.getCampaigns(districtId)
    }
}