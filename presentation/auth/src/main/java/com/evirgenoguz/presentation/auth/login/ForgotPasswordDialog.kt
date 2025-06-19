package com.evirgenoguz.presentation.auth.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import com.evirgenoguz.presentation.auth.databinding.BottomSheetForgotPasswordBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ForgotPasswordDialog : BottomSheetDialogFragment() {

    private var _binding: BottomSheetForgotPasswordBinding? = null
    private val binding get() = _binding!!

    override fun getTheme(): Int =
        com.google.android.material.R.style.Theme_Material3_Light_BottomSheetDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = BottomSheetForgotPasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonForgotPassword.setOnClickListener {
            val email = binding.textInputChangePasswordCurrentPassword.text.toString()
            parentFragmentManager.setFragmentResult(
                REQUEST_KEY,
                bundleOf(KEY_INPUT to email)
            )
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val REQUEST_KEY = "custom_input_request"
        const val KEY_INPUT = "input"
    }
}