package com.clappstertech.togatherandroid.deeplink

/**
 * Warning: Do not create deep links as objects because that deep links new instance can not be created.
 * New instances are important for its variables like @param isActive, isExecuted, isCanceled.
 */
sealed class DeepLink {

    var isInternalDeeplink: Boolean = false

    val isActive: Boolean
        get() = isExecuted.not() && isCanceled.not()

    var isExecuted: Boolean = false

    var isCanceled: Boolean = false

    fun executed() {
        isExecuted = true
    }

    fun cancel() {
        isCanceled = true
    }

    class ProfileDeepLink: DeepLink()
}
