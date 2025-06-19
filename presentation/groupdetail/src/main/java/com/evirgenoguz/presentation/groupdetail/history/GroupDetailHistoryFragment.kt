package com.evirgenoguz.presentation.groupdetail.history

import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import com.evirgenoguz.core.presentation.base.BaseFragmentViewPager
import com.evirgenoguz.core.presentation.ext.launchWhen
import com.evirgenoguz.core.presentation.util.BottomMarginItemDecorator
import com.evirgenoguz.presentation.groupdetail.databinding.FragmentGroupDetailHistoryBinding
import com.evirgenoguz.presentation.groupdetail.events.GroupEventsAdapter
import dagger.hilt.android.AndroidEntryPoint
import com.evirgenoguz.core.presentation.R as CoreRes

@AndroidEntryPoint
class GroupDetailHistoryFragment(
    private val groupId: Int
) :
    BaseFragmentViewPager<FragmentGroupDetailHistoryBinding, GroupDetailHistoryViewModel>(
        FragmentGroupDetailHistoryBinding::inflate
    ) {

    override val viewModel: GroupDetailHistoryViewModel by viewModels()

    private lateinit var groupEventsHistoryAdapter: GroupEventsAdapter

    override fun setupUI() {
        setAdapter()
        collectEvents()
        viewModel.onAction(GroupDetailHistoryAction.GetGroupEventsHistoryEvent(groupId))
    }

    private fun collectEvents() {
        launchWhen(Lifecycle.State.STARTED) {
            viewModel.events.collect { event ->
                when (event) {
                    is GroupDetailHistoryEvent.GroupEventsHistorySuccess -> historyEventSuccess()
                    is GroupDetailHistoryEvent.Error -> showErrorDialog(event.message)
                }
            }
        }
    }

    private fun historyEventSuccess() {
        groupEventsHistoryAdapter.submitList(viewModel.state.value.groupEventDomainModelList)
    }

    private fun setAdapter() = with(binding) {
        groupEventsHistoryAdapter = GroupEventsAdapter(
            groupEventDetailClickListener = {
                Toast.makeText(requireContext(), it.name.orEmpty(), Toast.LENGTH_SHORT).show()
            },
        )

        val bottomSpacing = resources.getDimensionPixelSize(CoreRes.dimen.spacing_medium)
        val topSpacing = resources.getDimensionPixelSize(CoreRes.dimen.spacing_medium)
        recyclerViewGroupDetailHistory.addItemDecoration(
            BottomMarginItemDecorator(
                topSpacing,
                bottomSpacing
            )
        )

        recyclerViewGroupDetailHistory.adapter = groupEventsHistoryAdapter
    }

}