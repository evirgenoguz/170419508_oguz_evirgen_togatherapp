package com.evirgenoguz.data.util

import com.evirgenoguz.domain.util.TokenManager
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject

class RefreshAuthenticator @Inject constructor(
    private val tokenManager: TokenManager
) : Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        return response.request.newBuilder()
            .header("Authorization", "Bearer ${tokenManager.getRefreshToken()}")
            .build()
    }
}