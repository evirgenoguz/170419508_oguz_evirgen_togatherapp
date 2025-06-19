package com.clappstertech.togatherandroid.deeplink

import com.clappstertech.togatherandroid.util.Url
import javax.inject.Inject
import javax.inject.Singleton

/**
 * This class only validates format of the Url. Does not checks deep link itself.
 */
@Singleton
class DeepLinkUrlValidator @Inject constructor() {

    fun isValid(url: Url): Boolean {
        return url.toString().isNotEmpty()
    }

}
