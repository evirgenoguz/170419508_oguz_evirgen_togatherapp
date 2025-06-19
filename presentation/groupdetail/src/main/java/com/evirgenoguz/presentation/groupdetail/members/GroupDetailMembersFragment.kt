package com.evirgenoguz.presentation.groupdetail.members

import android.content.Intent
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.evirgenoguz.core.presentation.base.BaseFragmentViewPager
import com.evirgenoguz.core.presentation.ext.launchWhen
import com.evirgenoguz.core.presentation.ext.showQuestionDialog
import com.evirgenoguz.domain.util.SessionManager
import com.evirgenoguz.presentation.groupdetail.R
import com.evirgenoguz.presentation.groupdetail.databinding.FragmentGroupDetailMembersBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GroupDetailMembersFragment(
    private val inviteCode: String
) : BaseFragmentViewPager<FragmentGroupDetailMembersBinding, GroupDetailMembersViewModel>(
    FragmentGroupDetailMembersBinding::inflate
) {

    override val viewModel: GroupDetailMembersViewModel by viewModels()
    private lateinit var groupMembersAdapter: GroupMembersAdapter

    override fun setupUI() {
        collectEvents()
        setAdapter()
        initListeners()
        viewModel.onAction(GroupDetailMembersAction.GetGroupMembers(inviteCode = inviteCode))
    }

    private fun collectEvents() {
        launchWhen(Lifecycle.State.STARTED) {
            viewModel.events.collect { event ->
                when (event) {
                    is GroupDetailMembersEvent.Error -> {}
                    GroupDetailMembersEvent.GroupMembersDataSuccess -> setAdapterData()
                    GroupDetailMembersEvent.CloseGroupSuccess -> closeGroupSuccess()
                    GroupDetailMembersEvent.KickUserSuccess -> kickUserSuccess()
                }
            }
        }
    }

    private fun kickUserSuccess() {
        Toast.makeText(requireContext(), "User kicked successfully", Toast.LENGTH_LONG).show()
        viewModel.onAction(GroupDetailMembersAction.GetGroupMembers(inviteCode = inviteCode))
    }

    private fun setAdapter() = with(binding) {
        groupMembersAdapter = GroupMembersAdapter(
            groupMemberKickListener = { groupMember ->
                if (groupMember.username != SessionManager.getCurrentUser()?.username) {
                    showQuestionDialog(
                        title = getString(R.string.kick_specific_user, groupMember.username),
                        message = getString(
                            R.string.are_you_sure_you_want_to_kick,
                            groupMember.username
                        ),
                        positiveButtonText = getString(R.string.kick),
                        negativeButtonText = getString(R.string.cancel),
                        onPositiveClick = {
                            viewModel.onAction(
                                GroupDetailMembersAction.OnKickUserClicked(
                                    inviteCode = inviteCode,
                                    username = groupMember.username.orEmpty()
                                )
                            )
                        }
                    )
                } else {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.you_cannot_kick_yourself),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        )

        recyclerViewGroupDetailMembers.adapter = groupMembersAdapter
    }

    private fun setAdapterData() {
        groupMembersAdapter.submitList(viewModel.state.value.members)
    }

    private fun closeGroupSuccess() {
        findNavController().navigateUp()
    }

    private fun initListeners() {
        binding.textViewDeleteGroup.setOnClickListener {
            showQuestionDialog(
                title = getString(R.string.close_group),
                message = getString(R.string.close_group_information),
                positiveButtonText = getString(R.string.close),
                negativeButtonText = getString(R.string.cancel),
                onPositiveClick = {
                    viewModel.onAction(GroupDetailMembersAction.OnDeleteGroupClicked(inviteCode))
                }
            )
        }
        binding.textViewShareInviteCode.setOnClickListener {
                val shareIntent = Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(Intent.EXTRA_SUBJECT, getString(R.string.join_our_group))
                    putExtra(Intent.EXTRA_TEXT,
                        getString(R.string.hey_join_our_group_using_this_group_id, inviteCode))
                }
                requireContext().startActivity(Intent.createChooser(shareIntent,
                    getString(R.string.share_group_id_via)))
        }
    }
}