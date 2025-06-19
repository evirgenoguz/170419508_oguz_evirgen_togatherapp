package com.evirgenoguz.presentation.home.group.create

import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.evirgenoguz.core.presentation.base.ViewModelFragment
import com.evirgenoguz.core.presentation.ext.launchWhen
import com.evirgenoguz.core.presentation.util.ButtonConfig
import com.evirgenoguz.presentation.home.databinding.FragmentCreateGroupBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class CreateGroupFragment :
    ViewModelFragment<FragmentCreateGroupBinding, CreateGroupViewModel>(FragmentCreateGroupBinding::inflate) {

    override val viewModel: CreateGroupViewModel by viewModels()

    override fun showBottomNavigation() = false

    override fun getToolbarButtons(): List<ButtonConfig> = listOf(
        ButtonConfig(
            title = resources.getString(com.evirgenoguz.core.presentation.R.string.close),
            iconResId = com.evirgenoguz.core.presentation.R.drawable.ic_close,
            onClick = { findNavController().navigateUp() }
        )
    )

    override fun setupUI() {
        setTitle(com.evirgenoguz.core.presentation.R.string.create_group)
        initListeners()
        collectEvents()
    }

    private fun collectEvents() {
        viewLifecycleOwner.launchWhen(Lifecycle.State.STARTED) {
            viewModel.events.collectLatest { event ->
                when (event) {
                    is CreateGroupEvent.Error -> showErrorDialog(event.errorMessage)
                    CreateGroupEvent.CreateGroupSuccess -> createGroupSuccess()
                }
            }
        }
    }

    private fun createGroupSuccess() {
        Toast.makeText(requireContext(), "Group Created Successfully", Toast.LENGTH_LONG).show()
        findNavController().navigateUp()
    }

    private fun initListeners() = with(binding) {
        buttonLoginLogin.setOnClickListener {
            viewModel.onAction(
                CreateGroupAction.OnCreateGroupClicked(
                    name = binding.textInputJoinGroupGroupName.text.toString(),
                    description = textInputCreateGroupGroupDescription.text.toString()
                )
            )
        }
    }

}