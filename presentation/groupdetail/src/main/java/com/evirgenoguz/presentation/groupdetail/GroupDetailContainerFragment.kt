package com.evirgenoguz.presentation.groupdetail

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.evirgenoguz.core.presentation.base.ViewModelFragment
import com.evirgenoguz.core.presentation.ext.launchWhen
import com.evirgenoguz.core.presentation.ext.showQuestionDialog
import com.evirgenoguz.core.presentation.util.ButtonConfig
import com.evirgenoguz.presentation.groupdetail.databinding.FragmentGroupDetailContainerBinding
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GroupDetailContainerFragment :
    ViewModelFragment<FragmentGroupDetailContainerBinding, GroupDetailContainerViewModel>(
        FragmentGroupDetailContainerBinding::inflate
    ) {

    companion object {
        const val INVITE_CODE = "inviteCode"
        const val GROUP_ID = "groupId"
    }

    override val viewModel by viewModels<GroupDetailContainerViewModel>()
    override fun showBottomNavigation() = false
    override fun showBackButton() = true
    override fun showAboveNavigationBar() = false

    private lateinit var inviteCode: String
    private var groupId: Int = 0

    override fun getToolbarButtons(): List<ButtonConfig> = listOf(
        ButtonConfig(
            title = resources.getString(R.string.leave_group),
            iconResId = R.drawable.ic_logout,
            onClick = {
                showQuestionDialog(
                    title = getString(R.string.leave_group),
                    message = getString(R.string.leave_group_confirmation),
                    positiveButtonText = getString(R.string.leave_group),
                    negativeButtonText = getString(R.string.cancel),
                    onPositiveClick = {
                        viewModel.onAction(GroupDetailContainerAction.OnLeaveGroup(inviteCode))
                    }
                )
            }
        )
    )

    override fun setupUI() {
        collectEvents()
        setupViewPager()
    }

    private fun setupViewPager() {
        inviteCode = arguments?.getString(INVITE_CODE).toString()
        groupId = arguments?.getInt(GROUP_ID, 0) ?: 0

        binding.viewPagerGroupDetail.adapter = GroupDetailPagerAdapter(this, inviteCode, groupId)

        TabLayoutMediator(
            binding.groupDetailTabLayout,
            binding.viewPagerGroupDetail
        ) { tab, position ->
            tab.text = when (position) {
                0 -> getString(R.string.events)
                1 -> getString(R.string.members)
                2 -> getString(R.string.history)
                else -> getString(R.string.blank)
            }
        }.attach()
    }

    private fun collectEvents() {
        launchWhen(Lifecycle.State.STARTED) {
            viewModel.events.collect { event ->
                when (event) {
                    GroupDetailContainerEvent.LeaveGroupSuccess -> findNavController().navigateUp()
                }
            }
        }
    }
}