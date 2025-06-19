package com.evirgenoguz.presentation.groupdetail.creategroupevent.setlocation

import android.location.Address
import android.location.Geocoder
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.evirgenoguz.core.presentation.base.ViewModelFragment
import com.evirgenoguz.core.presentation.ext.gone
import com.evirgenoguz.core.presentation.ext.visible
import com.evirgenoguz.location.LocationModel
import com.evirgenoguz.presentation.groupdetail.R
import com.evirgenoguz.presentation.groupdetail.creategroupevent.CreateGroupEventFragment
import com.evirgenoguz.presentation.groupdetail.databinding.FragmentSetGroupEventLocationBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.launch

class SetGroupEventLocationFragment :
    ViewModelFragment<FragmentSetGroupEventLocationBinding, SetGroupEventLocationViewModel>(
        FragmentSetGroupEventLocationBinding::inflate
    ) {

    override val viewModel: SetGroupEventLocationViewModel by viewModels()
    override fun showBackButton() = true
    override fun showBottomNavigation() = false
    override fun showAboveNavigationBar() = false

    private lateinit var googleMap: GoogleMap
    private var selectedLocation: LocationModel? = null
    private lateinit var markerLocation: LatLng

    override fun setupUI() {
        setTitle(getString(R.string.set_location))
        setupSearchBar()
        initListeners()
        binding.mapFragmentSetLocationMap.getFragment<SupportMapFragment>()
            .getMapAsync { googleMap ->
                this.googleMap = googleMap
                googleMap.setOnMapLongClickListener { latLng ->
                    googleMap.clear()
                    googleMap.addMarker(
                        MarkerOptions()
                            .position(latLng)
                            .title("Event Location")
                    )
                    markerLocation = latLng
                    locationSelected(markerLocation)
                }
            }
        prepareResultListener()
    }

    private fun locationSelected(markerLocation: LatLng) {
        val results =
            Geocoder(requireContext()).getFromLocation(
                markerLocation.latitude,
                markerLocation.longitude,
                1
            )
        val fullAddress = results?.getOrNull(0)

        val addressLine = fullAddress?.getAddressLine(0) ?: ""
        if (addressLine.isNotEmpty()) {
            binding.searchHintOverlay.text = addressLine
        }
    }

    private fun prepareResultListener() {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {

                val result = Bundle().apply {
                    selectedLocation?.let {
                        putSerializable(
                            CreateGroupEventFragment.SELECTED_LOCATION,
                            LocationModel(
                                selectedLocation?.latitude?.toDouble() ?: 0.0,
                                selectedLocation?.longitude?.toDouble() ?: 0.0
                            )
                        )
                    }
                }

                parentFragmentManager.setFragmentResult(
                    CreateGroupEventFragment.SELECTED_LOCATION,
                    result
                )
                findNavController().navigateUp()
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }

    private fun initListeners() {
        binding.fabGroupDetailCreateGroupEvent.setOnClickListener {
            if (::markerLocation.isInitialized) {
                selectedLocation = LocationModel(
                    markerLocation.latitude,
                    markerLocation.longitude
                )
            }
        }
    }

    private fun setupSearchBar() = with(binding) {
        searchViewSetLocation.setOnFocusChangeListener { _, hasFocus ->
            searchHintOverlay.visibility = if (hasFocus) View.GONE else View.VISIBLE
        }

        searchViewSetLocation.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                val locationString = searchViewSetLocation.query.toString()
                try {
                    val geocoder = Geocoder(requireContext())
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        geocoder.getFromLocationName(
                            locationString, 1,
                            object : Geocoder.GeocodeListener {
                                override fun onGeocode(addresses: MutableList<Address>) {
                                    val location = addresses.firstOrNull()
                                    location?.let {
                                        animateMap(googleMap, LatLng(it.latitude, it.longitude))
                                    }
                                }

                                override fun onError(errorMessage: String?) {
                                    Log.e("Geocoder", "Failed: $errorMessage")
                                }
                            }
                        )
                    } else {
                        val result = geocoder.getFromLocationName(locationString, 1)
                        val location = result?.firstOrNull()
                        location?.let {
                            animateMap(googleMap, LatLng(it.latitude, it.longitude))
                        }
                    }
                } catch (e: Exception) {
                    Log.e("Geocoder", "Failed: ${e.message}")
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (searchViewSetLocation.query.isNotEmpty()) searchHintOverlay.gone() else searchHintOverlay.visible()
                return true
            }
        })
    }

    private fun animateMap(map: GoogleMap, latLng: LatLng) {
        lifecycleScope.launch {
            map.clear()
            map.addMarker(MarkerOptions().position(latLng))
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
        }
    }
}