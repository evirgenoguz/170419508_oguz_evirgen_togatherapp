package com.evirgenoguz.presentation.profile

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.evirgenoguz.core.presentation.ext.parentPrimaryNavigationFragment
import com.evirgenoguz.domain.model.profile.ChangePasswordModel
import com.evirgenoguz.presentation.profile.databinding.BottomSheetChangePasswordBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChangePasswordBottomSheetDialog : BottomSheetDialogFragment() {

    private var _binding: BottomSheetChangePasswordBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ProfileViewModel by viewModels(
        ownerProducer = { parentPrimaryNavigationFragment }
    )

    override fun getTheme(): Int =
        com.google.android.material.R.style.Theme_Material3_Light_BottomSheetDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = BottomSheetChangePasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
    }

    private fun initListeners() {
        binding.buttonChangePasswordChange.setOnClickListener {
            viewModel.onAction(
                ProfileAction.OnChangePasswordClick(
                    ChangePasswordModel(
                        oldPassword = binding.textInputChangePasswordCurrentPassword.text.toString(),
                        newPassword = binding.textInputChangePasswordNewPassword.text.toString(),
                    )
                )
            )
            dismiss()
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        dialog?.setOnShowListener { it ->
            val d = it as BottomSheetDialog
            val bottomSheet =
                d.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            bottomSheet?.let {
                val behavior = BottomSheetBehavior.from(it)
                behavior.state = BottomSheetBehavior.STATE_EXPANDED
            }
        }
        return super.onCreateDialog(savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}