package com.evirgenoguz.data.network

import com.evirgenoguz.data.model.response.SampleResponse
import retrofit2.Response
import retrofit2.http.GET

interface SampleApi {
    @GET("character")
    suspend fun getSample(): Response<SampleResponse>
}