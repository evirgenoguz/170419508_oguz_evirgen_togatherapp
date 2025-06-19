package com.evirgenoguz.location

import android.content.Context
import androidx.annotation.RequiresPermission
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationListener
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

internal class FusedLocationHelper(context: Context, config: LocationUpdateConfig? = null) : LocationHelper {

    private val service: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)

    override var lastKnownLocation: LocationModel = LocationModel(0.0, 0.0)

    private val mainScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    private var requestUpdateConfiguration = config ?: LocationUpdateConfig()

    private val _location: MutableSharedFlow<LocationModel> = MutableSharedFlow(replay = 1)
    override val location: SharedFlow<LocationModel> = _location.asSharedFlow()

    private val locationListener = LocationListener {
        val locationModel = LocationModel(it.latitude, it.longitude)
        lastKnownLocation = locationModel
        _location.tryEmit(locationModel)
    }

    @RequiresPermission(anyOf = ["android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"])
    override suspend fun getLastKnownLocation(): LocationModel {
        val completable = CompletableDeferred<LocationModel>()
        mainScope.launch {
            service.lastLocation.addOnSuccessListener {
                val location = LocationModel(it.latitude, it.longitude)
                lastKnownLocation = location
                completable.complete(location)
            }.addOnFailureListener {
                completable.completeExceptionally(it)
            }
        }
        return completable.await()
    }

    @RequiresPermission(anyOf = ["android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"])
    override fun enableLocationUpdates() {

        val request = LocationRequest.Builder(
            requestUpdateConfiguration.priority.value,
            requestUpdateConfiguration.intervalInMillis
        ).build()

        service.requestLocationUpdates(
            request,
            locationListener,
            null
        )
    }

    override fun disableLocationUpdates() {
        service.removeLocationUpdates(locationListener)
    }

    override fun clear() {
        mainScope.cancel()
        service.removeLocationUpdates(locationListener)
        service.flushLocations()
    }

}