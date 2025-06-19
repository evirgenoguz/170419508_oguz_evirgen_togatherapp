package com.evirgenoguz.data.repository

import com.evirgenoguz.core.domain.util.Result
import com.evirgenoguz.data.datasource.remote.AuthRemoteDataSource
import com.evirgenoguz.domain.model.auth.AuthModel
import com.evirgenoguz.domain.model.auth.RegisterDomainModel
import com.evirgenoguz.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authRemoteDataSource: AuthRemoteDataSource
) : AuthRepository {
    override suspend fun login(
        email: String,
        password: String
    ): Result<AuthModel> {
        return authRemoteDataSource.login(email, password)
    }

    override suspend fun register(
        email: String,
        password: String,
        confirmPassword: String
    ): Result<RegisterDomainModel> {
        return authRemoteDataSource.register(
            email = email,
            password = password,
            confirmPassword = confirmPassword
        )
    }

    override suspend fun forgotPassword(email: String): Result<Unit> {
        return authRemoteDataSource.forgotPassword(email)
    }
}