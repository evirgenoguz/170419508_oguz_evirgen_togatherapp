package com.evirgenoguz.presentation.groupdetail.creategroupevent

import android.annotation.SuppressLint
import android.location.Geocoder
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.evirgenoguz.core.presentation.base.ViewModelFragment
import com.evirgenoguz.core.presentation.ext.gone
import com.evirgenoguz.core.presentation.ext.launchWhen
import com.evirgenoguz.core.presentation.ext.visible
import com.evirgenoguz.core.presentation.util.ButtonConfig
import com.evirgenoguz.domain.model.groupdetail.CreateEventLocationReqDomainModel
import com.evirgenoguz.domain.model.groupdetail.CreateGroupEventDomainModel
import com.evirgenoguz.domain.util.EventCategory
import com.evirgenoguz.location.LocationModel
import com.evirgenoguz.presentation.groupdetail.GroupDetailContainerFragment.Companion.GROUP_ID
import com.evirgenoguz.presentation.groupdetail.R
import com.evirgenoguz.presentation.groupdetail.databinding.FragmentCreateGroupEventBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.MaterialTimePicker.INPUT_MODE_KEYBOARD
import com.google.android.material.timepicker.TimeFormat
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date
import java.util.Locale
import com.evirgenoguz.core.presentation.R as CoreRes

@AndroidEntryPoint
class CreateGroupEventFragment :
    ViewModelFragment<FragmentCreateGroupEventBinding, CreateGroupEventViewModel>(
        FragmentCreateGroupEventBinding::inflate
    ) {

    companion object {
        const val SELECTED_LOCATION = "selected_location"
    }

    override val viewModel: CreateGroupEventViewModel by viewModels()

    private var groupId: Int = 0

    override fun getToolbarButtons(): List<ButtonConfig> {
        return listOf(
            ButtonConfig(
                title = resources.getString(CoreRes.string.close),
                iconResId = CoreRes.drawable.ic_close,
                onClick = { findNavController().navigateUp() }
            ))
    }

    override fun showBottomNavigation() = false
    override fun showAboveNavigationBar() = false

    @RequiresApi(Build.VERSION_CODES.O)
    override fun setupUI() {
        setTitle(getString(CoreRes.string.create_event))
        initArgs()
        initListeners()
        setEventTypeDropdown()
        setResultListener()
        setDateAndTimePickers()
        collectStates()
        collectEvents()
    }

    private fun collectEvents() {
        launchWhen(Lifecycle.State.STARTED) {
            viewModel.events.collect { event ->
                when (event) {
                    is CreateGroupEventEvent.Error -> {
                        showErrorDialog(event.message)
                    }

                    CreateGroupEventEvent.CreateEventSuccess -> {
                        Toast.makeText(
                            requireContext(),
                            "Event created successfully",
                            Toast.LENGTH_LONG
                        ).show()
                        findNavController().navigateUp()
                    }
                }
            }
        }
    }

    private fun initArgs() {
        groupId = arguments?.getInt(GROUP_ID, 0) ?: 0
    }

    private fun collectStates() {
        launchWhen(Lifecycle.State.STARTED) {
            viewModel.state.collect {
                binding.textViewCreateGroupEventFullAddress.text = it.fullAddress
                binding.textViewCreateGroupEventFullAddress.visible()
                prepareMap(it.locationModel)
            }
        }
    }

    @Suppress("DEPRECATION")
    private fun setResultListener() {
        parentFragmentManager.setFragmentResultListener(
            SELECTED_LOCATION,
            viewLifecycleOwner
        ) { _, bundle ->
            val selectedLocation = bundle.getSerializable(SELECTED_LOCATION) as LocationModel?
            selectedLocation?.let {
                viewModel.onAction(CreateGroupEventAction.OnSelectedLocationChanged(selectedLocation))
                getFullAddress(selectedLocation)
            }
        }
    }

    @Suppress("DEPRECATION")
    private fun getFullAddress(selectedLocation: LocationModel) {
        val results =
            Geocoder(requireContext()).getFromLocation(
                selectedLocation.latitude,
                selectedLocation.longitude,
                1
            )
        val fullAddress = results?.getOrNull(0)

        val addressLine = fullAddress?.getAddressLine(0) ?: ""
        val cityName = results?.getOrNull(0)?.adminArea
        val districtName = results?.getOrNull(0)?.subAdminArea

        if (addressLine.isNotEmpty()) {
            viewModel.onAction(
                CreateGroupEventAction.OnFullAddressChanged(
                    addressLine,
                    cityName.toString(),
                    districtName.toString()
                )
            )
        }
    }

    private fun prepareMap(selectedLocation: LocationModel) {
        lifecycleScope.launch {
            binding.mapFragmentCreateGroupEvent.getFragment<SupportMapFragment>()
                .getMapAsync { googleMap ->
                    googleMap.clear()
                    val latLng = LatLng(selectedLocation.latitude, selectedLocation.longitude)
                    googleMap.addMarker(MarkerOptions().position(latLng))
                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
                }
        }
    }

    private fun setEventTypeDropdown() {
        val items = EventCategory.entries.map { it.toString() }.toTypedArray()
        binding.autoCompleteTextViewEventType.setSimpleItems(items)
    }

    private fun createEventLocationReq(): CreateEventLocationReqDomainModel {
        val createEventLocationReqDomainModel = CreateEventLocationReqDomainModel(
            name = binding.textInputCreateGroupEventEventName.text.toString(),
            address = viewModel.state.value.fullAddress,
            latitude = viewModel.state.value.locationModel.latitude.toDouble(),
            longitude = viewModel.state.value.locationModel.longitude.toDouble(),
            cityId = viewModel.state.value.cityId,
            districtId = viewModel.state.value.districtId,
        )

        return createEventLocationReqDomainModel
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("DefaultLocale")
    private fun initListeners() {
        binding.textViewCreateGroupEventFullAddress.gone()
        binding.linearLayoutSetLocation.setOnClickListener {
            findNavController().navigate(R.id.action_createGroupEventFragment_to_setGroupEventLocationFragment)
        }
        val todayMillis = MaterialDatePicker.todayInUtcMilliseconds()
        val formattedDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            .format(Date(todayMillis))

        binding.textViewCreateGroupEventStartDate.text = formattedDate
        binding.textViewCreateGroupEventEndDate.text = formattedDate

        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val formattedTime = String.format("%02d:%02d", hour, minute)
        binding.textViewCreateGroupEventEndTime.text = formattedTime
        binding.textViewCreateGroupEventStartTime.text = formattedTime

        binding.buttonCreateGroupEventCreate.setOnClickListener {

            val startDateString =
                "${binding.textViewCreateGroupEventStartDate.text} ${binding.textViewCreateGroupEventStartTime.text}"
            val endDateString =
                "${binding.textViewCreateGroupEventEndDate.text} ${binding.textViewCreateGroupEventEndTime.text}"
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
            val startDate: LocalDateTime = LocalDateTime.parse(startDateString, formatter)
            val endDate: LocalDateTime = LocalDateTime.parse(endDateString, formatter)

            val createEventLocationReqDomainModel = createEventLocationReq()

            viewModel.onAction(
                CreateGroupEventAction.OnCreateEventClicked(
                    CreateGroupEventDomainModel(
                        name = binding.textInputCreateGroupEventEventName.text.toString(),
                        description = binding.textInputCreateGroupEventEventDescription.text.toString(),
                        startDate = startDate,
                        endDate = endDate,
                        createdByGroupId = groupId,
                        eventCategory = EventCategory.getValue(
                            binding.autoCompleteTextViewEventType.text.toString().uppercase()
                        ),
                        totalParticipants = binding.counterViewCreateGroupEventMaxParticipants.getValue()
                    ),
                    createEventLocationReqDomainModel,
                )
            )
        }
    }

    @SuppressLint("DefaultLocale")
    private fun setDateAndTimePickers() {
        binding.imageButtonSelectStartDate.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText(getString(R.string.select_start_date))
                .build()

            datePicker.show(parentFragmentManager, "start_date_picker")

            datePicker.addOnPositiveButtonClickListener { selectedDate ->
                val formatted = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                    .format(Date(selectedDate))

                binding.textViewCreateGroupEventStartDate.text = formatted
            }
        }

        binding.imageButtonSelectEndDate.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText(getString(R.string.select_end_date))
                .build()

            datePicker.show(parentFragmentManager, "end_date_picker")

            datePicker.addOnPositiveButtonClickListener { selectedDate ->
                val formatted = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                    .format(Date(selectedDate))

                binding.textViewCreateGroupEventEndDate.text = formatted
            }
        }

        binding.imageButtonCreateGroupEventStartTime.setOnClickListener {
            val picker =
                MaterialTimePicker.Builder()
                    .setTimeFormat(TimeFormat.CLOCK_24H)
                    .setInputMode(INPUT_MODE_KEYBOARD)
                    .setTitleText("Select Start Time")
                    .build()

            picker.show(parentFragmentManager, "start_time_picker")

            picker.addOnPositiveButtonClickListener {
                val formatted = String.format("%02d:%02d", picker.hour, picker.minute)
                binding.textViewCreateGroupEventStartTime.text = formatted
            }
        }

        binding.imageButtonCreateGroupEventEndTime.setOnClickListener {
            val picker =
                MaterialTimePicker.Builder()
                    .setTimeFormat(TimeFormat.CLOCK_24H)
                    .setInputMode(INPUT_MODE_KEYBOARD)
                    .setTitleText("Select End Time")
                    .build()

            picker.show(parentFragmentManager, "end_time_picker")

            picker.addOnPositiveButtonClickListener {
                val formatted = String.format("%02d:%02d", picker.hour, picker.minute)
                binding.textViewCreateGroupEventEndTime.text = formatted
            }
        }
    }
}