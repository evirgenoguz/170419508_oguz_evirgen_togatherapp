package com.evirgenoguz.presentation.auth.register

import android.view.View.OnFocusChangeListener
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.evirgenoguz.core.presentation.base.ViewModelFragment
import com.evirgenoguz.core.presentation.ext.launchWhen
import com.evirgenoguz.core.presentation.ext.setClickableSpan
import com.evirgenoguz.presentation.auth.R
import com.evirgenoguz.presentation.auth.databinding.FragmentRegisterBinding
import com.evirgenoguz.presentation.auth.util.Validator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment :
    ViewModelFragment<FragmentRegisterBinding, RegisterViewModel>(FragmentRegisterBinding::inflate) {

    override val viewModel: RegisterViewModel by viewModels()

    override fun showAppBar(): Boolean = false
    override fun showBottomNavigation(): Boolean = false

    override fun setupUI() {
        setDoYouHaveAnAccountText()
        initListeners()
        collectEvents()

        binding.textInputRegisterEmail.setText(viewModel.state.value.email)
        binding.textInputRegisterPassword.setText(viewModel.state.value.password)
        binding.textInputRegisterConfirmPassword.setText(viewModel.state.value.confirmPassword)
    }

    private fun collectEvents() {
        viewLifecycleOwner.launchWhen(Lifecycle.State.STARTED) {
            viewModel.events.collect { event ->
                when (event) {
                    RegisterEvent.RegisterSuccess -> {
                        findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                        Toast.makeText(requireContext(), "Register Success", Toast.LENGTH_SHORT)
                    }

                    is RegisterEvent.Error -> {
                        showErrorDialog(event.errorMessage)
                        Toast.makeText(
                            requireContext(),
                            event.errorMessage,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    private fun initListeners() = with(binding) {
        textInputRegisterPassword.doAfterTextChanged {
            textFieldRegisterPassword.helperText =
                Validator.getPasswordValidationMessage(it.toString())
        }
        textInputRegisterPassword.onFocusChangeListener = OnFocusChangeListener { _, hasFocus ->
            if (hasFocus.not()) textFieldRegisterPassword.isHelperTextEnabled = false
        }

        textInputRegisterEmail.doAfterTextChanged {
            textFieldRegisterEmail.helperText = Validator.isValidEmail(it.toString())
        }
        textInputRegisterEmail.onFocusChangeListener = OnFocusChangeListener { _, hasFocus ->
            if (hasFocus.not()) textFieldRegisterPassword.isHelperTextEnabled = false
        }

        buttonRegister.setOnClickListener {
            viewModel.onAction(
                RegisterAction.OnRegisterClick(
                    email = binding.textInputRegisterEmail.text.toString(),
                    password = binding.textInputRegisterPassword.text.toString(),
                    confirmPassword = binding.textInputRegisterPassword.text.toString()
                )
            )
        }
    }

    private fun setDoYouHaveAnAccountText() {
        binding.textViewAlreadyHaveAnAccount.setClickableSpan(
            fullText = getString(com.evirgenoguz.core.presentation.R.string.already_have_an_account),
            clickablePart = getString(com.evirgenoguz.core.presentation.R.string.login),
            color = ContextCompat.getColor(
                requireContext(),
                com.evirgenoguz.core.presentation.R.color.colorPrimary
            )
        ) {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }
    }

}