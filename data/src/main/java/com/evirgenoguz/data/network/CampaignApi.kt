package com.evirgenoguz.data.network

import com.evirgenoguz.data.model.response.GetCampaignResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CampaignApi {
    @GET("campaigns/list")
    suspend fun getCampaigns(
        @Query("cityId") cityId: Int,
    ): Response<List<GetCampaignResponse>>
}