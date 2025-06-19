package com.evirgenoguz.data.network

import com.evirgenoguz.data.model.response.LoginResponse
import retrofit2.Response
import retrofit2.http.POST

interface RefreshTokenApi {
    @POST("auth/refresh-token")
    suspend fun refreshToken(): Response<LoginResponse>
}