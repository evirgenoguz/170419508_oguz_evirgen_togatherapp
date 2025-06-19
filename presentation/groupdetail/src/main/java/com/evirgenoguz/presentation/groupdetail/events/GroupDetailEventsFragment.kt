package com.evirgenoguz.presentation.groupdetail.events

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.evirgenoguz.core.presentation.base.BaseFragmentViewPager
import com.evirgenoguz.core.presentation.ext.gone
import com.evirgenoguz.core.presentation.ext.launchWhen
import com.evirgenoguz.core.presentation.ext.visible
import com.evirgenoguz.core.presentation.util.BottomMarginItemDecorator
import com.evirgenoguz.presentation.groupdetail.GroupDetailContainerFragment.Companion.GROUP_ID
import com.evirgenoguz.presentation.groupdetail.R
import com.evirgenoguz.presentation.groupdetail.databinding.FragmentGroupDetailEventsBinding
import dagger.hilt.android.AndroidEntryPoint
import com.evirgenoguz.core.presentation.R as CoreRes

@AndroidEntryPoint
class GroupDetailEventsFragment(
    private val inviteCode: String,
    private val groupId: Int
) :
    BaseFragmentViewPager<FragmentGroupDetailEventsBinding, GroupDetailEventsViewModel>(
        FragmentGroupDetailEventsBinding::inflate
    ) {

    override val viewModel: GroupDetailEventsViewModel by viewModels()

    private lateinit var groupEventsAdapter: GroupEventsAdapter
    override fun setupUI() {
        viewModel.onAction(GroupDetailEventsAction.GetGroupEvents(groupId))
        setAdapter()
        collectEvents()
        initListeners()
    }

    private fun collectEvents() {
        launchWhen(Lifecycle.State.STARTED) {
            viewModel.events.collect { event ->
                when (event) {
                    is GroupDetailEventsEvent.GroupEventsSuccess -> setAdapterData()

                    is GroupDetailEventsEvent.Error -> showErrorDialog(event.message)

                }
            }
        }
    }

    private fun initListeners() {
        binding.fabGroupDetailCreateGroupEvent.setOnClickListener {
            val bundle = Bundle().apply {
                putInt(GROUP_ID, groupId)
            }
            findNavController().navigate(R.id.action_groupDetailContainerFragment_to_createGroupEventFragment, bundle)
        }
    }

    private fun setAdapterData() {
        if (viewModel.state.value.groupEventList.isNotEmpty()) {
            groupEventsAdapter.submitList(viewModel.state.value.groupEventList)
            binding.textViewGroupDetailEventsNoEvent.gone()
            binding.recyclerViewGroupDetailEvents.visible()
        } else {
            binding.recyclerViewGroupDetailEvents.gone()
            binding.textViewGroupDetailEventsNoEvent.visible()
        }
    }

    private fun setAdapter() = with(binding) {
        groupEventsAdapter = GroupEventsAdapter(
            groupEventDetailClickListener = {
                val bundle = Bundle().apply {
                    putSerializable(GROUP_EVENT_ID, it.id)
                }
                findNavController().navigate(
                    R.id.action_groupDetailContainerFragment_to_groupEventDetailFragment,
                    bundle
                )
            },
        )

        val bottomSpacing = resources.getDimensionPixelSize(CoreRes.dimen.spacing_medium)
        val topSpacing = resources.getDimensionPixelSize(CoreRes.dimen.spacing_medium)
        recyclerViewGroupDetailEvents.addItemDecoration(
            BottomMarginItemDecorator(
                topSpacing,
                bottomSpacing
            )
        )

        recyclerViewGroupDetailEvents.adapter = groupEventsAdapter
    }

    companion object {
        const val GROUP_EVENT_ID = "groupEventId"
    }
}