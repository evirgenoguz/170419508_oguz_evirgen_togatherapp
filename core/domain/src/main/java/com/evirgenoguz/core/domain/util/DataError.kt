package com.evirgenoguz.core.domain.util

sealed interface DataError: Error {
    enum class Network(val errorMessage: String): DataError {
        REQUEST_TIMEOUT("Request timeout"),
        UNAUTHORIZED("Unauthorized"),
        CONFLICT("Conflict"),
        TOO_MANY_REQUESTS("Too many requests"),
        NO_INTERNET("No internet connection"),
        PAYLOAD_TOO_LARGE("Payload too large"),
        SERVER_ERROR("Server error"),
        SERIALIZATION("Serialization error"),
        UNKNOWN("Unknown error")
    }

    enum class Local: DataError {
        DISK_FULL
    }
}