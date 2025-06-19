package com.clappstertech.togatherandroid.deeplink

import com.clappstertech.togatherandroid.deeplink.route.DeepLinkRoute
import com.clappstertech.togatherandroid.util.Url
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DeepLinkResolver @Inject constructor(
    private val deepLinkUrlValidator: DeepLinkUrlValidator
) {

    fun resolve(args: DeepLinkArgs): DeepLink? {
        val url = Url(args.url)
        val isUrlValid = deepLinkUrlValidator.isValid(url)
        if (isUrlValid.not()) return null

        val deepLinkRoute = DeepLinkRoute.all.firstOrNull { it.match(url) }

        val deeplink = deepLinkRoute?.parse(url)

        return deeplink
    }
}
