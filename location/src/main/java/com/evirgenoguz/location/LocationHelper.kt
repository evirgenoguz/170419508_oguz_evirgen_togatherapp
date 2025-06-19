package com.evirgenoguz.location

import kotlinx.coroutines.flow.SharedFlow

interface LocationHelper {

    var lastKnownLocation: LocationModel

    suspend fun getLastKnownLocation(): LocationModel

    fun enableLocationUpdates()

    fun disableLocationUpdates()

    fun clear()

    val location: SharedFlow<LocationModel>
}