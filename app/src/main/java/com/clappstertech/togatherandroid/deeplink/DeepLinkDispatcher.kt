package com.clappstertech.togatherandroid.deeplink

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DeepLinkDispatcher @Inject constructor(
    private val deepLinkResolver: DeepLinkResolver,
) {

    private val deeplinkScope = CoroutineScope(Dispatchers.Main)

    private val _deeplinkFlow = MutableSharedFlow<DeepLink>(replay = DEEP_LINK_REPLAY_COUNT)
    val deeplinkFlow: SharedFlow<DeepLink> = _deeplinkFlow

    var currentDeepLink: DeepLink? = null
        private set

    fun dispatch(deepLink: DeepLink) {
        deeplinkScope.launch {
            _deeplinkFlow.emit(deepLink)
        }
        currentDeepLink = deepLink
    }

    fun dispatch(args: DeepLinkArgs) {
        deepLinkResolver.resolve(args)?.also { deepLink ->
            dispatch(deepLink)
        }
    }

    companion object {
        private const val DEEP_LINK_REPLAY_COUNT = 1
    }
}
