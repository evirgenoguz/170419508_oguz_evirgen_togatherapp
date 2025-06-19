package com.evirgenoguz.domain.usecase.auth

import com.evirgenoguz.core.domain.util.Result
import com.evirgenoguz.domain.model.auth.RegisterDomainModel
import com.evirgenoguz.domain.repository.AuthRepository
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(
        email: String,
        password: String,
        confirmPassword: String
    ): Result<RegisterDomainModel> {
        return repository.register(
            email = email,
            password = password,
            confirmPassword = confirmPassword
        )
    }
}