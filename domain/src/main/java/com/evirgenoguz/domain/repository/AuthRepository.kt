package com.evirgenoguz.domain.repository

import com.evirgenoguz.core.domain.util.Result
import com.evirgenoguz.domain.model.auth.AuthModel
import com.evirgenoguz.domain.model.auth.RegisterDomainModel

interface AuthRepository {
    suspend fun login(email: String, password: String): Result<AuthModel>
    suspend fun register(
        email: String,
        password: String,
        confirmPassword: String
    ): Result<RegisterDomainModel>

    suspend fun forgotPassword(email: String): Result<Unit>
}