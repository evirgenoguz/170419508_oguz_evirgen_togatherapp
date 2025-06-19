package com.evirgenoguz.presentation.profile.editprofile

import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import coil3.load
import com.evirgenoguz.core.presentation.base.ViewModelFragment
import com.evirgenoguz.core.presentation.ext.launchWhen
import com.evirgenoguz.domain.model.profile.ProfileUserModel
import com.evirgenoguz.domain.usecase.profile.UpdateUserModel
import com.evirgenoguz.presentation.profile.ProfileFragment
import com.evirgenoguz.presentation.profile.R
import com.evirgenoguz.presentation.profile.databinding.FragmentEditProfileBinding
import com.google.android.material.datepicker.MaterialDatePicker
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@AndroidEntryPoint
class EditProfileFragment :
    ViewModelFragment<FragmentEditProfileBinding, EditProfileViewModel>(FragmentEditProfileBinding::inflate) {

    private lateinit var userModel: ProfileUserModel
    private lateinit var pickVisualMedia: ActivityResultLauncher<PickVisualMediaRequest>

    override val viewModel by viewModels<EditProfileViewModel>()

    override fun showBottomNavigation() = false
    override fun showBackButton() = true

    override fun setupUI() {
        getArgs()
        setUserCurrentInfo()
        setTitle(R.string.profile_action_edit_profile)
        setGenderDropdown()
        setDatePicker()
        collectEvents()
        initImagePicker()
        initListeners()
    }

    private fun setUserCurrentInfo() = with(binding) {
        userModel.profilePictureUrl?.let {
            imageViewEditProfileAvatar.load(userModel.profilePictureUrl)
        }
        textInputEditProfileName.setText(userModel.firstName)
        textInputEditProfileLastName.setText(userModel.lastName)
        textInputEditProfileNickname.setText(userModel.username)
        textInputEditProfileBirthDate.setText(userModel.birthDate)
        textInputEditProfileBio.setText(userModel.bio)
        if (userModel.gender.isNotEmpty()) {
            autoCompleteTextViewGender.setText(
                Gender.getGenderText(
                    userModel.gender.toCharArray().first().toString().uppercase()
                )
            )
        }
    }

    private fun getArgs() {
        userModel = arguments?.getSerializable(ProfileFragment.USER_MODEL) as ProfileUserModel
    }

    private fun initListeners() = with(binding) {
        buttonEditProfileUpdate.setOnClickListener {
            viewModel.onAction(
                EditProfileAction.OnUpdateProfileClick(
                    updateUserModel = UpdateUserModel(
                        firstName = textInputEditProfileName.text.toString(),
                        lastName = textInputEditProfileLastName.text.toString(),
                        userName = textInputEditProfileNickname.text.toString(),
                        birthDate = textInputEditProfileBirthDate.text.toString(),
                        gender = Gender.getGender(
                            autoCompleteTextViewGender.text.toString().uppercase()
                        ).value,
                        bio = textInputEditProfileBio.text.toString()
                    )
                )
            )
        }

        binding.textViewEditProfileEditAvatar.setOnClickListener {
            pickVisualMedia.launch(PickVisualMediaRequest(PickVisualMedia.ImageOnly))
        }
    }

    private fun collectEvents() {
        launchWhen(Lifecycle.State.STARTED) {
            viewModel.events.collect { event ->
                when (event) {
                    is EditProfileEvent.UpdateProfileError -> showErrorDialog(event.message)
                    is EditProfileEvent.UpdateProfileSuccess -> updateProfileSuccess()
                }

            }
        }
    }

    private fun initImagePicker() {
        pickVisualMedia =
            registerForActivityResult(PickVisualMedia()) { uri ->
                if (uri != null) {
                    binding.imageViewEditProfileAvatar.setImageURI(uri)
                    val inputStream = requireContext().contentResolver.openInputStream(uri)

                    inputStream?.let {
                        viewModel.onAction(EditProfileAction.OnAvatarSelected(inputStream))
                    }
                }
            }
    }

    private fun updateProfileSuccess() {
        Toast.makeText(requireContext(), "Profile updated successfully", Toast.LENGTH_SHORT).show()
        findNavController().navigateUp()
    }

    private fun setDatePicker() {
        val datePicker =
            MaterialDatePicker.Builder.datePicker()
                .setTitleText(getString(R.string.select_birthday))
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .build()

        datePicker.addOnPositiveButtonClickListener { selectedDate ->
            val formatted = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                .format(Date(selectedDate))

            binding.textInputEditProfileBirthDate.setText(formatted)
        }

        binding.textInputEditProfileBirthDate.setOnClickListener {
            datePicker.show(parentFragmentManager, MATERIAL_DATE_PICKER)
        }
    }

    private fun setGenderDropdown() {
        val items = arrayOf(Gender.FEMALE.name, Gender.MALE.name, Gender.OTHER.name)
        binding.autoCompleteTextViewGender.setSimpleItems(items)
    }

    companion object {
        const val MATERIAL_DATE_PICKER = "MATERIAL_DATE_PICKER"
    }
}