package com.evirgenoguz.data.logger

import android.util.Log
import com.evirgenoguz.domain.model.Event
import com.evirgenoguz.domain.util.EventLogger

class ConsoleEventLogger : EventLogger {
    override fun logCustomEvent(event: Event) {
        Log.d("ConsoleEventLogger", "Logged custom event: $event.name")
    }

    override fun logScreen(screenName: String, screenClass: String?) {
        Log.d("ConsoleEventLogger", "Logged screen: $screenName")
    }

    override fun setUserProperty(key: String, value: String) {
        println("ConsoleEventLogger - User Property: $key = $value")
    }

    override fun setUserId(userId: String?) {
        println("ConsoleEventLogger - User ID: $userId")
    }
}