package com.evirgenoguz.domain.usecase.auth

import com.evirgenoguz.core.domain.util.Result
import com.evirgenoguz.domain.model.auth.AuthModel
import com.evirgenoguz.domain.repository.AuthRepository
import com.evirgenoguz.domain.util.TokenManager
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val tokenManager: TokenManager
) {
    suspend operator fun invoke(email: String, password: String): Result<AuthModel> {
        return authRepository.login(email, password).also { result ->
            if (result is Result.Success) {
                tokenManager.saveTokens(result.data.accessToken, result.data.refreshToken)
            }
        }
    }
}