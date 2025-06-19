package com.evirgenoguz.presentation.profile

import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat.getColor
import androidx.core.net.toUri
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import coil3.load
import com.evirgenoguz.core.presentation.base.ViewModelFragment
import com.evirgenoguz.core.presentation.ext.gone
import com.evirgenoguz.core.presentation.ext.invisible
import com.evirgenoguz.core.presentation.ext.launchWhen
import com.evirgenoguz.core.presentation.ext.setClickableSpan
import com.evirgenoguz.core.presentation.ext.showQuestionDialog
import com.evirgenoguz.core.presentation.ext.visible
import com.evirgenoguz.core.presentation.util.ButtonConfig
import com.evirgenoguz.core.presentation.util.Deeplink
import com.evirgenoguz.presentation.profile.databinding.FragmentProfileBinding
import dagger.hilt.android.AndroidEntryPoint
import com.evirgenoguz.core.presentation.R as CoreResource

@AndroidEntryPoint
class ProfileFragment :
    ViewModelFragment<FragmentProfileBinding, ProfileViewModel>(FragmentProfileBinding::inflate) {

    override val viewModel: ProfileViewModel by viewModels()

    override fun setupUI() {
        viewModel.getUserInformation()
        setTitle(getString(CoreResource.string.app_name))
        initListeners()
        setupUserInfo()
        setupProfileActionsAdapter()
        collectEvent()
    }

    private fun initListeners() = with(binding) {

    }

    private fun collectEvent() {
        launchWhen(Lifecycle.State.STARTED) {
            viewModel.events.collect { event ->
                when (event) {
                    is ProfileEvent.Error -> showErrorDialog(event.errorMessage)
                    ProfileEvent.DataSuccess -> setupUserInfo()
                    ProfileEvent.LogoutSuccess -> navigateToLogin()
                    is ProfileEvent.ChangePasswordError -> passwordChangeError()
                    ProfileEvent.ChangePasswordSuccess -> passwordChangeSuccess()
                }
            }
        }
    }

    private fun navigateToLogin() {
        val navOptions = NavOptions.Builder()
            .setPopUpTo(R.id.profileFragment, true)
            .build()

        val request = NavDeepLinkRequest.Builder
            .fromUri(Deeplink.TO_LOGIN.link.toUri())
            .build()
        findNavController().navigate(request, navOptions)
    }

    private fun setupUserInfo() = with(binding) {
        viewModel.state.value.userModel.apply {
            if (credibility < 50) {
                binding.layoutUserCard.invisible()
                binding.textViewNotEnoughInfoForUserCard.setClickableSpan(
                    fullText = getString(R.string.edit_profile_information_warning),
                    clickablePart = getString(R.string.edit_profile_information_spannable_part),
                    color = getColor(requireContext(), CoreResource.color.colorPrimary),
                    onClick = {
                        val bundle = Bundle().apply {
                            putSerializable(USER_MODEL, viewModel.state.value.userModel)
                        }
                        findNavController().navigate(
                            R.id.action_profileFragment_to_editProfileFragment,
                            bundle
                        )
                    }
                )
                binding.textViewNotEnoughInfoForUserCard.visible()
            } else {
                binding.layoutUserCard.visible()
                binding.textViewNotEnoughInfoForUserCard.gone()
                profilePictureUrl?.let {
                    imageViewHomeAvatar.load(it)
                }
                textViewHomeUserName.text = fullName
                textViewHomeUserNickName.text = username
                textViewHomeUserBiography.text = bio
                textViewHomeUserCredit.text = credibility.toString()
            }
        }
    }

    private fun passwordChangeSuccess() {
        Toast.makeText(requireContext(), "Password changed successfully", Toast.LENGTH_SHORT).show()
        viewModel.onAction(ProfileAction.OnLogoutClick)
    }

    private fun passwordChangeError() {
        Toast.makeText(requireContext(), "Password change error", Toast.LENGTH_SHORT).show()
    }

    private fun setupProfileActionsAdapter() {
        val actions = listOf(

            ProfileActionItem(
                titleRes = R.string.profile_action_edit_profile,
                onClick = {
                    val bundle = Bundle().apply {
                        putSerializable(USER_MODEL, viewModel.state.value.userModel)
                    }
                    findNavController().navigate(
                        R.id.action_profileFragment_to_editProfileFragment,
                        bundle
                    )
                }
            ),

            ProfileActionItem(
                titleRes = R.string.profile_action_change_location,
                onClick = {
                    findNavController().navigate(R.id.action_profileFragment_to_changeLocationFragment)
                }
            ),

            ProfileActionItem(
                titleRes = R.string.profile_action_history,
                onClick = {
                    Toast.makeText(requireContext(), "Clicked at history", Toast.LENGTH_SHORT)
                        .show()
                }
            ),

            ProfileActionItem(
                titleRes = R.string.profile_action_change_password,
                onClick = {
                    findNavController().navigate(R.id.action_profileFragment_to_changePasswordBottomSheetDialog)
                }
            ),

            ProfileActionItem(
                titleRes = R.string.profile_action_privacy_policy,
                onClick = {
                    Toast.makeText(
                        requireContext(),
                        "Clicked at privacy policy",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            ),
        )

        binding.recyclerViewProfileActions.adapter = ProfileActionsAdapter(actions)
    }

    override fun getToolbarButtons(): List<ButtonConfig> {
        return listOf(
            ButtonConfig(
                title = getString(R.string.profile_menu_item_logout),
                iconResId = R.drawable.ic_logout,
                onClick = {
                    showQuestionDialog(
                        title = getString(R.string.profile_logout_dialog_title),
                        positiveButtonText = getString(R.string.profile_menu_item_logout),
                        negativeButtonText = getString(CoreResource.string.dismiss),
                        onPositiveClick = {
                            viewModel.onAction(ProfileAction.OnLogoutClick)
                        },
                        onNegativeClick = {
                            Toast.makeText(requireContext(), "Dismiss", Toast.LENGTH_SHORT).show()
                        }
                    )
                }
            )
        )
    }

    companion object {
        const val USER_MODEL = "user_model"
    }
}