package com.evirgenoguz.core.domain.util

data class NetworkError(
    val errorCode: Int = -1,
    val message: String = "Something went wrong",
    val type: DataError.Network = DataError.Network.UNKNOWN
): Error