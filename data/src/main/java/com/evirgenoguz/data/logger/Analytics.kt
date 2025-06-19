package com.evirgenoguz.data.logger

import com.evirgenoguz.domain.model.Event
import com.evirgenoguz.domain.util.EventLogger
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Analytics @Inject constructor(
    private val loggers: Set<@JvmSuppressWildcards EventLogger>
) : EventLogger {
    override fun logCustomEvent(event: Event) {
        loggers.forEach { it.logCustomEvent(event) }
    }

    override fun logScreen(screenName: String, screenClass: String?) {
        loggers.forEach { it.logScreen(screenName, screenClass) }
    }

    override fun setUserProperty(key: String, value: String) {
        loggers.forEach { it.setUserProperty(key, value) }
    }

    override fun setUserId(userId: String?) {
        loggers.forEach { it.setUserId(userId) }
    }
}