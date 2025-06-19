package com.evirgenoguz.domain.usecase.campaign

import com.evirgenoguz.domain.repository.CampaignRepository
import javax.inject.Inject

class GetCampaignUseCase @Inject constructor(
    private val campaignRepository: CampaignRepository
) {
    suspend operator fun invoke(districtId: Int) = campaignRepository.getCampaigns(districtId)
}