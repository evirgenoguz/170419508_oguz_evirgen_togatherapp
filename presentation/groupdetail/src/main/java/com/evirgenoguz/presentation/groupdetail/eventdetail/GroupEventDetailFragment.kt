package com.evirgenoguz.presentation.groupdetail.eventdetail

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.core.net.toUri
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import coil3.load
import com.evirgenoguz.core.presentation.base.ViewModelFragment
import com.evirgenoguz.core.presentation.ext.launchWhen
import com.evirgenoguz.domain.model.groupdetail.GroupEventDomainModel
import com.evirgenoguz.domain.util.EventCategory
import com.evirgenoguz.core.presentation.util.BottomMarginItemDecorator
import com.evirgenoguz.presentation.groupdetail.R
import com.evirgenoguz.presentation.groupdetail.databinding.FragmentGroupEventDetailBinding
import com.evirgenoguz.presentation.groupdetail.events.GroupDetailEventsFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import java.time.format.DateTimeFormatter
import kotlin.time.DurationUnit
import kotlin.time.toDuration
import com.evirgenoguz.core.presentation.R as CoreRes

@AndroidEntryPoint
class GroupEventDetailFragment :
    ViewModelFragment<FragmentGroupEventDetailBinding, GroupEventDetailViewModel>(
        FragmentGroupEventDetailBinding::inflate
    ) {

    companion object {
        const val USER_NAME_LIST = "usernameList"
    }

    override val viewModel: GroupEventDetailViewModel by viewModels()
    override fun showBottomNavigation() = false
    override fun showBackButton() = true

    private var groupEventId: Int = 0
    private lateinit var groupEventDetailMembersAdapter: GroupEventDetailMembersAdapter

    override fun setupUI() {
        getArgs()
        setAdapter()
        initListeners()
        collectEvents()
        viewModel.onAction(GroupEventDetailAction.GetGroupEventDetail(groupEventId))
    }

    private fun collectEvents() {
        launchWhen(Lifecycle.State.STARTED) {
            viewModel.events.collect { event ->
                when (event) {
                    is GroupEventDetailEvent.GroupEventDetailSuccess -> {
                        fillData(event.groupEventDetailDomainModel)
                    }

                    is GroupEventDetailEvent.Error -> showErrorDialog(event.message)
                    GroupEventDetailEvent.JoinGroupEventSuccess -> joinSuccess()
                    GroupEventDetailEvent.LeaveGroupEventSuccess -> leaveSuccess()
                    GroupEventDetailEvent.ChangedJoinState -> setButtonTextAndFunction()
                }
            }
        }
    }

    private fun leaveSuccess() {
        Toast.makeText(requireContext(), "Successfully leave the event", Toast.LENGTH_SHORT).show()
        viewModel.onAction(GroupEventDetailAction.ChangeJoinState(false))
    }

    private fun joinSuccess() {
        Toast.makeText(requireContext(), "Successfully join the event", Toast.LENGTH_SHORT).show()
        viewModel.onAction(GroupEventDetailAction.ChangeJoinState(true))
    }

    private fun fillData(groupEventDetail: GroupEventDomainModel) = with(binding) {
        setButtonTextAndFunction()
        imageViewGroupEventDetailEventImage.load(EventCategory.getImageUrl(groupEventDetail.eventCategory))
        textViewGroupEventDetailTitle.text = groupEventDetail.name
        textViewGroupEventDetailDate.text = groupEventDetail.startDate?.let {
            formatToUserReadable(it)
        }
        textViewGroupEventDetailDescription.text = groupEventDetail.description
        groupEventDetailMembersAdapter.submitList(groupEventDetail.participants.toList())
    }

    private fun setButtonTextAndFunction() {
        val state = viewModel.state.value
        val button = binding.buttonGroupEventDetailDynamic
        val event = state.groupEventDomainModel

        if (state.alreadyJoined && event?.startDate?.let { isLessThanTwoHoursLeft(it) } == true) {
            button.text = getString(R.string.see_live_locations)
            val usernameList = UsernameList().apply {
                event.participants.forEach { participant ->
                    participant.user?.username?.let { usernameList.add(it) }
                }
            }
            val bundle = Bundle().apply {
                putSerializable(USER_NAME_LIST, usernameList)
            }
            button.setOnClickListener {
                findNavController().navigate(
                    R.id.action_groupEventDetailFragment_to_gatherMapFragment,
                    bundle
                )
            }
        } else {
            if (state.alreadyJoined) {
                button.text = getString(R.string.leave)
                button.setOnClickListener {
                    viewModel.onAction(GroupEventDetailAction.LeaveGroupEvent(groupEventId))
                }
            } else {
                button.text = getString(R.string.join)
                button.setOnClickListener {
                    viewModel.onAction(GroupEventDetailAction.JoinGroupEvent(groupEventId))
                }
            }
        }
    }


    private fun getArgs() {
        groupEventId = arguments?.getInt(GroupDetailEventsFragment.GROUP_EVENT_ID, 0) ?: 0
    }

    private fun initListeners() {
        binding.textButtonGroupEventDetailLocation.setOnClickListener {
            val lat = viewModel.state.value.groupEventDomainModel?.eventLocation?.latitude ?: 0.0
            val lng = viewModel.state.value.groupEventDomainModel?.eventLocation?.longitude ?: 0.0

            val uri = "geo:$lat,$lng?q=$lat,$lng".toUri()
            val intent = Intent(Intent.ACTION_VIEW, uri)
            intent.setPackage("com.google.android.apps.maps")

            if (intent.resolveActivity(requireContext().packageManager) != null) {
                startActivity(intent)
            }
        }
        binding.buttonGroupEventDetailDynamic.setOnClickListener {
            findNavController().navigate(R.id.action_groupEventDetailFragment_to_gatherMapFragment)
        }
    }

    private fun setAdapter() {
        groupEventDetailMembersAdapter = GroupEventDetailMembersAdapter()
        binding.recyclerViewGroupEventDetailMembers.adapter = groupEventDetailMembersAdapter
        val bottomSpacing = resources.getDimensionPixelSize(CoreRes.dimen.spacing_medium)
        val topSpacing = resources.getDimensionPixelSize(CoreRes.dimen.spacing_medium)
        binding.recyclerViewGroupEventDetailMembers.addItemDecoration(
            BottomMarginItemDecorator(
                topSpacing,
                bottomSpacing
            )
        )
    }

    fun isLessThanTwoHoursLeft(datetimeString: String): Boolean {
        val timeZone = TimeZone.currentSystemDefault()

        val targetDateTime = LocalDateTime.parse(datetimeString)

        val nowInstant = Clock.System.now()
        val targetInstant = targetDateTime.toInstant(timeZone)

        val durationMillis = targetInstant.toEpochMilliseconds() - nowInstant.toEpochMilliseconds()

        if (durationMillis <= 0) return false

        val hoursLeft = durationMillis.toDuration(DurationUnit.MILLISECONDS).inWholeHours
        return hoursLeft < 2
    }


    fun formatToUserReadable(datetimeString: String): String {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val ldt = LocalDateTime.parse(datetimeString)

            val javaLdt = java.time.LocalDateTime.of(
                ldt.year,
                ldt.monthNumber,
                ldt.dayOfMonth,
                ldt.hour,
                ldt.minute
            )


            val formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy HH:mm")

            return javaLdt.format(formatter)
        } else {
            return datetimeString
        }
    }
}