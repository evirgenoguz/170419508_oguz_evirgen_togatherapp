package com.clappstertech.togatherandroid.util

import android.net.Uri
import androidx.core.net.toUri

@JvmInline
value class Url(val uri: Uri) {

    constructor(url: String) : this(url.toUri())

    val value: String
        get() = host + path

    val path: String
        get() = uri.path.orEmpty()

    val host: String
        get() {
            val host = uri.host.orEmpty()
            return host

        }

    override fun toString(): String {
        return uri.toString()
    }
}