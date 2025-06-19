package com.evirgenoguz.core.presentation

import com.evirgenoguz.core.domain.util.DataError
import com.evirgenoguz.core.domain.util.Error

fun Error.asString(): String {
    return when (this) {
        DataError.Local.DISK_FULL -> "Failed to save the item because your disk is full."

        DataError.Network.REQUEST_TIMEOUT -> "The request timed out."

        DataError.Network.TOO_MANY_REQUESTS -> "You\'ve hit your rate limit."

        DataError.Network.NO_INTERNET -> "Couldn\'t reach server, please check your connection."

        DataError.Network.PAYLOAD_TOO_LARGE -> "The file you\\'re trying to upload is too large."

        DataError.Network.SERVER_ERROR -> "Oops, something went wrong, please try again."

        DataError.Network.SERIALIZATION -> "Oops, couldn\'t parse data."

        else -> "An unknown error occurred."
    }
}