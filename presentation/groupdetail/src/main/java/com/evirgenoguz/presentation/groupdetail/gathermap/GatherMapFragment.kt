package com.evirgenoguz.presentation.groupdetail.gathermap

import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import com.evirgenoguz.core.presentation.base.ViewModelFragment
import com.evirgenoguz.core.presentation.ext.launchWhen
import com.evirgenoguz.core.presentation.permission.LocationPermission
import com.evirgenoguz.domain.model.groupdetail.UpdateLiveLocationDomainModel
import com.evirgenoguz.location.LocationHelper
import com.evirgenoguz.location.LocationHelperBuilder
import com.evirgenoguz.location.LocationPriority
import com.evirgenoguz.presentation.groupdetail.databinding.FragmentGatherMapBinding
import com.evirgenoguz.presentation.groupdetail.eventdetail.GroupEventDetailFragment.Companion.USER_NAME_LIST
import com.evirgenoguz.presentation.groupdetail.eventdetail.UsernameList
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class GatherMapFragment :
    ViewModelFragment<FragmentGatherMapBinding, GatherMapViewModel>(FragmentGatherMapBinding::inflate) {

    override val viewModel: GatherMapViewModel by viewModels()
    override fun showBottomNavigation() = false
    override fun showBackButton() = true
    override fun showAboveNavigationBar() = false

    private lateinit var locationPermission: LocationPermission
    private val locationHelper: LocationHelper by lazy {
        LocationHelperBuilder()
            .with(requireContext())
            .setIntervalInMillis(2000L * 10)
            .setPriority(LocationPriority.HIGH_ACCURACY)
            .build()
    }

    private lateinit var usernames: UsernameList
    val points: MutableList<LatLng> = mutableListOf()

    override fun setupUI() {
        locationPermission = LocationPermission(this)
        getArgs()
        checkLocationPermission()
        collectLocationUpdate()
        collectEvents()
    }

    private fun getArgs() {
        usernames = arguments?.getSerializable(USER_NAME_LIST) as UsernameList
    }

    private fun collectEvents() {
        launchWhen(Lifecycle.State.STARTED) {
            viewModel.events.collect {
                when (it) {
                    GatherMapEvent.LocationsSuccess -> {
                        markUserOnMap(viewModel.state.value.locations)
                    }
                }
            }
        }
    }

    private fun collectLocationUpdate() {
        launchWhen(Lifecycle.State.STARTED) {
            locationHelper.location.collectLatest {
                viewModel.onAction(
                    GatherMapAction.GetLocationsByUsernames(usernames.usernameList)
                )
            }
        }
    }

    private fun checkLocationPermission() {
        if (!locationPermission.isGranted()) {
            locationPermission.onSuccess {
                Toast.makeText(requireContext(), "Permission Granted", Toast.LENGTH_SHORT).show()
                locationHelper.enableLocationUpdates()
            }.onDeny {
                Toast.makeText(requireContext(), "Permission Denied", Toast.LENGTH_SHORT).show()
            }.request()
        } else {
            locationHelper.enableLocationUpdates()
        }
    }

    private fun showLastKnowLocation() {
        binding.mapFragmentGatherMap.getFragment<SupportMapFragment>().getMapAsync { googleMap ->
            locationHelper.lastKnownLocation.let {
                val myLatLng = LatLng(it.latitude, it.longitude)
                points.add(myLatLng)
                points.forEachIndexed { index, latLng ->
                    googleMap.addMarker(
                        MarkerOptions()
                            .position(latLng)
                            .title("Location ${index + 1}")
                    )
                }
                val boundsBuilder = LatLngBounds.Builder()
                points.forEach { boundsBuilder.include(it) }

                val bounds = boundsBuilder.build()
                googleMap.animateCamera(
                    CameraUpdateFactory.newLatLngBounds(
                        bounds,
                        100
                    )
                )
            }
        }
    }

    fun markUserOnMap(userList: List<UpdateLiveLocationDomainModel>) {
        if (userList.isEmpty()) return
        binding.mapFragmentGatherMap.getFragment<SupportMapFragment>().getMapAsync { googleMap ->
            googleMap.clear()
            userList.forEach { user ->
                val position = LatLng(user.latitude, user.longitude)
                googleMap.addMarker(
                    MarkerOptions()
                        .position(position)
                        .title(user.username)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)) // optional color
                )
            }
            val boundsBuilder = LatLngBounds.Builder()
            userList.forEach { boundsBuilder.include(LatLng(it.latitude, it.longitude)) }

            val bounds = boundsBuilder.build()
            googleMap.animateCamera(
                CameraUpdateFactory.newLatLngBounds(
                    bounds,
                    300
                )
            )
        }
    }
}