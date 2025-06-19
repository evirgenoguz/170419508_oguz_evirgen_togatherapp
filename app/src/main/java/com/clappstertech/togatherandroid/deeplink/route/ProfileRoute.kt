package com.clappstertech.togatherandroid.deeplink.route

import com.clappstertech.togatherandroid.deeplink.DeepLink
import com.clappstertech.togatherandroid.util.Url

class ProfileRoute: DeepLinkRoute() {

    override fun match(url: Url): Boolean {
        return url.path.endsWith("profile")
    }

    override fun parse(url: Url): DeepLink {
        return DeepLink.ProfileDeepLink()
    }
}