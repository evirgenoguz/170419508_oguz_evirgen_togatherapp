package com.evirgenoguz.core.data.networking

import com.evirgenoguz.core.domain.util.NetworkError

data class ErrorResponseModel(
    val status: String,
    val timestamp: String,
    val message: String?,
    val debugMessage: String?,
    val subErrors: List<Any>?
)

fun ErrorResponseModel.toDomainError(): NetworkError {
    return NetworkError(
        errorCode = 0,
        message = this.debugMessage ?: message.orEmpty()
    )
}
