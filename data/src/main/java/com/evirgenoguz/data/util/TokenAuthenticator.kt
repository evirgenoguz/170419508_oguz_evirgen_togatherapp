package com.evirgenoguz.data.util

import com.evirgenoguz.data.network.RefreshTokenApi
import com.evirgenoguz.domain.util.TokenManager
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject

class TokenAuthenticator @Inject constructor(
    private val tokenManager: TokenManager,
    private val refreshAuthApi: RefreshTokenApi
) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        if (responseCount(response) >= 2) return null

        val newAccessToken = try {
            runBlocking {
                tokenManager.clearTokens()
                val result = refreshAuthApi.refreshToken()
                val token = result.body()?.accessToken
                if (!token.isNullOrBlank()) {
                    tokenManager.saveTokens(
                        result.body()?.accessToken.orEmpty(),
                        result.body()?.refreshToken.orEmpty()
                    )
                    token
                } else null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }

        return newAccessToken?.let {
            response.request.newBuilder()
                .header("Authorization", "Bearer $it")
                .build()
        }
    }
}

private fun responseCount(response: Response): Int {
    var count = 1
    var prior = response.priorResponse
    while (prior != null) {
        count++
        prior = prior.priorResponse
    }
    return count
}