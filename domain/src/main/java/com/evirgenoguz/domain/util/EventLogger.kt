package com.evirgenoguz.domain.util

import com.evirgenoguz.domain.model.Event

interface EventLogger {
    fun logCustomEvent(event: Event)
    fun logScreen(screenName: String, screenClass: String?)
    fun setUserProperty(key: String, value: String)
    fun setUserId(userId: String?)

}