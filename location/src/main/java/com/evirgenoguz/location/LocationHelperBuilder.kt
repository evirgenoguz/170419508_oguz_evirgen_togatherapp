package com.evirgenoguz.location

import android.content.Context

class LocationHelperBuilder {

    private var context: Context? = null
    private var config = LocationUpdateConfig()

    fun with(context: Context): LocationHelperBuilder {
        this.context = context
        return this
    }

    fun setIntervalInMillis(intervalInMillis: Long): LocationHelperBuilder {
        this.config = config.copy(intervalInMillis = intervalInMillis)
        return this
    }

    fun setPriority(priority: LocationPriority): LocationHelperBuilder {
        this.config = config.copy(priority = priority)
        return this
    }

    fun build(): LocationHelper {

        if (context == null) {
            throw IllegalArgumentException("Context cannot be null, set context by LocationHelperBuilder.with(context)")
        }

        return FusedLocationHelper(context!!, config)
    }

}