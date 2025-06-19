package com.evirgenoguz.presentation.home.group.join

import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.evirgenoguz.core.presentation.base.ViewModelFragment
import com.evirgenoguz.core.presentation.ext.launchWhen
import com.evirgenoguz.core.presentation.util.ButtonConfig
import com.evirgenoguz.presentation.home.databinding.FragmentJoinGroupBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class JoinGroupFragment :
    ViewModelFragment<FragmentJoinGroupBinding, JoinGroupViewModel>(FragmentJoinGroupBinding::inflate) {

    override val viewModel: JoinGroupViewModel by viewModels()

    override fun showBottomNavigation() = false

    override fun getToolbarButtons() = listOf(
        ButtonConfig(
            title = resources.getString(com.evirgenoguz.core.presentation.R.string.close),
            iconResId = com.evirgenoguz.core.presentation.R.drawable.ic_close,
            onClick = { findNavController().navigateUp() }
        )
    )

    override fun setupUI() {
        setTitle(com.evirgenoguz.core.presentation.R.string.join_group)
        initListeners()
        collectEvents()
    }

    private fun collectEvents() {
        launchWhen(Lifecycle.State.STARTED) {
            viewModel.events.collectLatest { event ->
                when (event) {
                    is JoinGroupEvent.JoinGroupError -> showErrorDialog(event.message)
                    JoinGroupEvent.JoinGroupSuccess -> joinGroupSuccess()
                }
            }
        }
    }

    private fun joinGroupSuccess() {
        Toast.makeText(requireContext(), "Group Joined Successfully", Toast.LENGTH_LONG).show()
        findNavController().navigateUp()
    }

    private fun initListeners() = with(binding) {
        buttonJoinGroupJoin.setOnClickListener {
            viewModel.onAction(
                JoinGroupAction.OnJoinGroupClick(
                    inviteCode = textInputJoinGroupInviteCode.text.toString()
                )
            )
        }
    }

}