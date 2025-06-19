package com.evirgenoguz.domain.usecase.auth

import com.evirgenoguz.domain.util.TokenManager
import javax.inject.Inject

class GetAccessTokenUseCase @Inject constructor(
    private val repository: TokenManager
) {
    operator fun invoke(): String? {
        return repository.getAccessToken()
    }
}