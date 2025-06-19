package com.evirgenoguz.data.datasource.remote

import com.evirgenoguz.core.data.networking.safeCall
import com.evirgenoguz.core.domain.util.Result
import com.evirgenoguz.data.model.request.LoginRequestModel
import com.evirgenoguz.data.model.request.RegisterRequestModel
import com.evirgenoguz.data.model.response.toAuthModel
import com.evirgenoguz.data.model.response.toDomainModel
import com.evirgenoguz.data.network.AuthApi
import com.evirgenoguz.domain.model.auth.AuthModel
import com.evirgenoguz.domain.model.auth.RegisterDomainModel

class AuthRemoteDataSource(
    private val authApi: AuthApi
) {
    suspend fun login(email: String, password: String): Result<AuthModel> {
        val result = safeCall {
            authApi.login(
                LoginRequestModel(
                    email = email,
                    password = password
                )
            )
        }
        return when (result) {
            is Result.Error -> {
                Result.Error(result.error)
            }

            is Result.Success -> {
                Result.Success(result.data.toAuthModel())
            }
        }
    }

    suspend fun register(
        email: String,
        password: String,
        confirmPassword: String
    ): Result<RegisterDomainModel> {
        val result = safeCall {
            val registerRequestModel = RegisterRequestModel(
                email = email,
                password = password,
                confirmPassword = confirmPassword,
            )
            authApi.register(registerRequestModel)
        }
        return when (result) {
            is Result.Error -> {
                Result.Error(result.error)
            }

            is Result.Success -> {
                Result.Success(data = result.data.toDomainModel())
            }
        }
    }

    suspend fun forgotPassword(email: String): Result<Unit> {
        val result = safeCall {
            authApi.forgotPassword(email)
        }

        return when (result) {
            is Result.Error -> Result.Error(result.error)
            is Result.Success -> Result.Success(Unit)
        }
    }
}