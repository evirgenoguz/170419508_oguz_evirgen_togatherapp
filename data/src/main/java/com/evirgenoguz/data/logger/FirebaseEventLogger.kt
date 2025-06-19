package com.evirgenoguz.data.logger

import android.os.Bundle
import com.evirgenoguz.domain.model.Event
import com.evirgenoguz.domain.util.EventLogger
import com.google.firebase.analytics.FirebaseAnalytics

class FirebaseEventLogger(
    private val firebaseAnalytics: FirebaseAnalytics
): EventLogger {
    override fun logCustomEvent(event: Event) {
        val bundle = Bundle().apply {
            event.params.forEach { (key, value) ->
                when (value) {
                    is String -> putString(key, value)
                    is Int -> putInt(key, value)
                    is Double -> putDouble(key, value)
                    is Long -> putLong(key, value)
                    is Boolean -> putBoolean(key, value)
                    else -> putString(key, value.toString())
                }
            }
        }
        firebaseAnalytics.logEvent(event.name, bundle)
    }

    override fun logScreen(screenName: String, screenClass: String?) {
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, Bundle().apply {
            putString(FirebaseAnalytics.Param.SCREEN_NAME, screenName)
            putString(FirebaseAnalytics.Param.SCREEN_CLASS, screenClass)
        })
    }

    override fun setUserProperty(key: String, value: String) {
        firebaseAnalytics.setUserProperty(key, value)
    }

    override fun setUserId(userId: String?) {
        firebaseAnalytics.setUserId(userId)
    }
}