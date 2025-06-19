package com.evirgenoguz.domain.usecase.auth

import com.evirgenoguz.core.domain.util.Result
import com.evirgenoguz.domain.repository.AuthRepository
import javax.inject.Inject

class ForgotPasswordUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(email: String): Result<Unit> {
        return authRepository.forgotPassword(email)
    }
}