package com.evirgenoguz.data.network

import com.evirgenoguz.data.model.request.LoginRequestModel
import com.evirgenoguz.data.model.request.RegisterRequestModel
import com.evirgenoguz.data.model.response.LoginResponse
import com.evirgenoguz.data.model.response.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthApi {

    @POST("auth/login")
    suspend fun login(
        @Body loginRequestModel: LoginRequestModel
    ): Response<LoginResponse>

    @POST("auth/register")
    suspend fun register(
        @Body registerRequestModel: RegisterRequestModel
    ): Response<RegisterResponse>

    @POST("auth/reset-password-request")
    suspend fun forgotPassword(
        @Query("email") email: String
    ): Response<Unit>
}