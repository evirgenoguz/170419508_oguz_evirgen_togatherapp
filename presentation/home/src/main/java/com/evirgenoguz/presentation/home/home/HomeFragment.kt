package com.evirgenoguz.presentation.home.home

import android.location.Geocoder
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.net.toUri
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.fragment.findNavController
import coil3.load
import com.evirgenoguz.core.presentation.base.ViewModelFragment
import com.evirgenoguz.core.presentation.ext.gone
import com.evirgenoguz.core.presentation.ext.invisible
import com.evirgenoguz.core.presentation.ext.launchWhen
import com.evirgenoguz.core.presentation.ext.showQuestionDialog
import com.evirgenoguz.core.presentation.ext.visible
import com.evirgenoguz.core.presentation.permission.LocationPermission
import com.evirgenoguz.core.presentation.util.ButtonConfig
import com.evirgenoguz.core.presentation.util.Deeplink
import com.evirgenoguz.domain.util.EventCategory
import com.evirgenoguz.domain.util.SessionManager
import com.evirgenoguz.location.LocationHelper
import com.evirgenoguz.location.LocationHelperBuilder
import com.evirgenoguz.location.LocationPriority
import com.evirgenoguz.presentation.home.R
import com.evirgenoguz.presentation.home.databinding.FragmentHomeBinding
import com.evirgenoguz.presentation.home.home.state.HomeAction
import com.evirgenoguz.presentation.home.home.state.HomeEvent
import com.evirgenoguz.presentation.home.home.state.SelectedType
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toInstant
import kotlin.time.DurationUnit
import kotlin.time.toDuration

@AndroidEntryPoint
class HomeFragment :
    ViewModelFragment<FragmentHomeBinding, HomeViewModel>(FragmentHomeBinding::inflate) {

    override val viewModel: HomeViewModel by viewModels()

    private lateinit var groupsAdapter: GroupsAdapter
    private lateinit var eventsAdapter: EventsAdapter

    private lateinit var locationPermission: LocationPermission

    private val locationHelper: LocationHelper by lazy {
        LocationHelperBuilder()
            .with(requireContext())
            .setIntervalInMillis(1000L * 10)
            .setPriority(LocationPriority.HIGH_ACCURACY)
            .build()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        locationPermission = LocationPermission(this)
    }

    override fun getToolbarButtons(): List<ButtonConfig> = listOf(
        ButtonConfig(
            title = resources.getString(com.evirgenoguz.core.presentation.R.string.settings),
            iconResId = com.evirgenoguz.core.presentation.R.drawable.ic_bell,
            onClick = {
                Toast.makeText(requireContext(), "Inbox Clicked", Toast.LENGTH_SHORT).show()
            }
        )
    )

    @RequiresApi(Build.VERSION_CODES.O)
    override fun setupUI() {
        setTitle(com.evirgenoguz.core.presentation.R.string.app_name)
        initialRequests()
        initListeners()
        setAdapters()
        collectEvents()
        collectHomeState()
        setEventsAdapterData()
        checkLocationPermission()
    }

    private fun checkLocationPermission() {
        if (!locationPermission.isGranted()) {
            locationPermission.onSuccess {
                locationHelper.enableLocationUpdates()
                Toast.makeText(requireContext(), "Permission Granted", Toast.LENGTH_SHORT).show()
            }.onDeny {
                Toast.makeText(requireContext(), "Permission Denied", Toast.LENGTH_SHORT).show()
            }.request()
        }
    }

    private fun initialRequests() {
        viewModel.getGroups()
        viewModel.getMyEvents()
        viewModel.getUserInformation()
    }

    private fun collectHomeState() {
        viewLifecycleOwner.launchWhen(Lifecycle.State.STARTED) {
            viewModel.state.collectLatest { state ->
                when (state.selectedType) {
                    SelectedType.Group -> makeGroupVisible()
                    SelectedType.Event -> makeEventVisible()
                }
            }
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    @Suppress("DEPRECATION")
    private fun collectEvents() {
        viewLifecycleOwner.launchWhen(Lifecycle.State.STARTED) {
            viewModel.events.collectLatest { event ->
                when (event) {
                    is HomeEvent.Error -> showErrorDialog(event.errorMessage)
                    HomeEvent.GroupsDataSuccess -> setGroupsAdapterData()
                    HomeEvent.UserDataSuccess -> checkUserHasLocation()
                    HomeEvent.MyEventsSuccess -> {
                        setEventsAdapterData()
                        setNextEvent()
                    }
                }
            }
        }
        viewLifecycleOwner.launchWhen(Lifecycle.State.STARTED) {
            locationHelper.location.collectLatest {
                val results =
                    Geocoder(requireContext()).getFromLocation(it.latitude, it.longitude, 1)
                val result = results?.getOrNull(0) ?: return@collectLatest

                Log.d(
                    "HomeFragment",
                    """
                            countryName: ${result.countryName},
                            countryCode: ${result.countryCode},
                            adminArea: ${result.adminArea},
                            subAdminArea: ${result.subAdminArea},
                            locality: ${result.locality},
                            subLocality: ${result.subLocality},
                            thoroughfare: ${result.thoroughfare},
                            subThoroughfare: ${result.subThoroughfare},
                        """.trimIndent()
                )
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setNextEvent() {
        val nextEvent = viewModel.state.value.userEventList.getOrNull(0) ?: return
        SessionManager.setIsNextEventLessThanTwoHoursAway(isLessThanTwoHoursLeft(nextEvent.startDate))
        binding.layoutHomeNextEvent.textViewEventName.text = nextEvent.name
        binding.layoutHomeNextEvent.imageViewNextEventLayout.load(
            EventCategory.getImageUrl(nextEvent.eventCategory)
        )
        binding.root.setOnClickListener {
            navigateEventDetail(nextEvent.id)
        }
    }

    private fun checkUserHasLocation() {
        val userCountry = viewModel.state.value.userModel.country
        val userCity = viewModel.state.value.userModel.city
        if (userCity.isEmpty() || userCountry.isEmpty()) {
            showQuestionDialog(
                title = getString(R.string.set_location),
                message = getString(R.string.set_location_description),
                positiveButtonText = getString(R.string.set_location),
                negativeButtonText = getString(R.string.cancel),
                onPositiveClick = { navigateToChangeLocation() }
            )
        }
    }

    private fun navigateToChangeLocation() {
        val request = NavDeepLinkRequest.Builder
            .fromUri(Deeplink.TO_CHANGE_LOCATION.link.toUri())
            .build()

        findNavController().navigate(request)
    }

    private fun setGroupsAdapterData() {
        groupsAdapter.submitList(viewModel.state.value.userGroupList)
    }

    private fun setEventsAdapterData() {
        eventsAdapter.submitList(viewModel.state.value.userEventList)
    }

    private fun initListeners() = with(binding) {
        toggleButtonHomeGroupsOrEvents.check(R.id.buttonHomeGroups)

        toggleButtonHomeGroupsOrEvents.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (checkedId == R.id.buttonHomeGroups && isChecked) {
                viewModel.onAction(HomeAction.OnGroupButtonClick)
            } else if (checkedId == R.id.buttonHomeEvents && isChecked) {
                viewModel.onAction(HomeAction.OnEventButtonClick)
            }
        }

        fabHomeCreateGroup.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_createGroupFragment)
        }

        fabHomeJoinGroup.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_joinGroupFragment)
        }
    }

    private fun setAdapters() = with(binding) {
        groupsAdapter = GroupsAdapter {
            val inviteCode = it.inviteCode
            val uri = "${Deeplink.TO_GROUP_DETAIL.link}/$inviteCode/group_id/${it.id}".toUri()
            val request = NavDeepLinkRequest.Builder
                .fromUri(uri)
                .build()

            findNavController().navigate(request)
        }
        recyclerViewHomeGroups.adapter = groupsAdapter

        eventsAdapter = EventsAdapter { navigateEventDetail(it.id) }
        recyclerViewHomeEvents.adapter = eventsAdapter
    }

    private fun navigateEventDetail(eventId: Int?) {
        eventId ?: return
        val uri = "${Deeplink.TO_EVENT_DETAIL.link}/${eventId}".toUri()
        val request = NavDeepLinkRequest.Builder
            .fromUri(uri)
            .build()
        findNavController().navigate(request)
    }

    private fun makeGroupVisible() = with(binding) {
        if (viewModel.state.value.userGroupList.isNotEmpty()) {
            recyclerViewHomeGroups.visible()
            textViewHomeNoGroup.gone()
        } else {
            textViewHomeNoGroup.visible()
        }
        recyclerViewHomeEvents.invisible()
        textViewHomeNoEvent.gone()
        fabHomeCreateEvent.invisible()
        fabHomeCreateGroup.visible()
        expendJoinFab()
    }

    private fun makeEventVisible() = with(binding) {
        if (viewModel.state.value.userEventList.isNotEmpty()) {
            recyclerViewHomeEvents.visible()
            textViewHomeNoEvent.gone()
        } else {
            textViewHomeNoEvent.visible()
        }
        recyclerViewHomeGroups.invisible()
        textViewHomeNoGroup.gone()
        fabHomeCreateEvent.visible()
        fabHomeCreateGroup.invisible()
        collapseJoinFab()
    }

    private fun expendJoinFab() {
        var joinFabTranslationY: Float

        with(binding) {
            fabHomeJoinGroup.visible()
            val spacingSmall =
                resources.getDimension(com.evirgenoguz.core.presentation.R.dimen.spacing_small)
            root.post {
                joinFabTranslationY = fabHomeJoinGroup.height + spacingSmall

                fabHomeJoinGroup
                    .animate()
                    .translationY(-joinFabTranslationY)
            }
        }
    }

    private fun collapseJoinFab() {
        with(binding) {
            fabHomeJoinGroup
                .animate()
                .translationY(0f)
                .withEndAction {
                    fabHomeJoinGroup.invisible()
                }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun isLessThanTwoHoursLeft(datetimeString: String?): Boolean {
        datetimeString ?: return false
        val timeZone = TimeZone.currentSystemDefault()

        val targetDateTime = LocalDateTime.parse(datetimeString)

        val nowInstant = Clock.System.now()
        val targetInstant = targetDateTime.toInstant(timeZone)

        val durationMillis = targetInstant.toEpochMilliseconds() - nowInstant.toEpochMilliseconds()

        if (durationMillis <= 0) return false

        val hoursLeft = durationMillis.toDuration(DurationUnit.MILLISECONDS).inWholeHours
        return hoursLeft < 2
//        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        locationHelper.clear()
    }
}