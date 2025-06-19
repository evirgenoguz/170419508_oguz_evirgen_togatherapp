package com.clappstertech.togatherandroid.deeplink.route

import com.clappstertech.togatherandroid.deeplink.DeepLink
import com.clappstertech.togatherandroid.util.Url

abstract class DeepLinkRoute {

    abstract fun match(url: Url): Boolean

    abstract fun parse(url: Url): DeepLink

    companion object {
        val all: List<DeepLinkRoute> = listOf(
            ProfileRoute()
        )
    }
}