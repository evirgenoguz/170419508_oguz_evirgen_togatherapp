package com.evirgenoguz.location

internal data class LocationUpdateConfig(
    val intervalInMillis: Long = DEFAULT_INTERVAL_IN_MILLIS,
    val priority: LocationPriority = LocationPriority.HIGH_ACCURACY,
) {

    companion object {
        private const val DEFAULT_INTERVAL_IN_MILLIS = 1000L * 60
    }
}
