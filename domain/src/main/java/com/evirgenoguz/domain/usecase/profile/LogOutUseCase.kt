package com.evirgenoguz.domain.usecase.profile

import com.evirgenoguz.core.domain.util.Result
import com.evirgenoguz.domain.model.profile.ProfileUserModel
import com.evirgenoguz.domain.util.SessionManager
import com.evirgenoguz.domain.util.TokenManager
import javax.inject.Inject

class LogOutUseCase @Inject constructor(
    private val tokenManager: TokenManager
) {
    operator fun invoke(): Result<Unit> {
        tokenManager.clearTokens()
        SessionManager.updateUser(ProfileUserModel())
        return Result.Success(Unit)
    }
}