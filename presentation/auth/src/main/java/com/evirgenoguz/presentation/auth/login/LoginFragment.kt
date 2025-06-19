package com.evirgenoguz.presentation.auth.login

import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.evirgenoguz.core.presentation.base.ViewModelFragment
import com.evirgenoguz.core.presentation.ext.launchWhen
import com.evirgenoguz.core.presentation.ext.setClickableSpan
import com.evirgenoguz.core.presentation.ext.showInfoDialog
import com.evirgenoguz.core.presentation.util.Deeplink
import com.evirgenoguz.presentation.auth.R
import com.evirgenoguz.presentation.auth.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment :
    ViewModelFragment<FragmentLoginBinding, LoginViewModel>(FragmentLoginBinding::inflate) {

    override val viewModel: LoginViewModel by viewModels()

    override fun showAppBar(): Boolean = false
    override fun showBottomNavigation(): Boolean = false

    override fun setupUI() {
        setDontYouHaveAnAccountText()
        setListeners()
        collectEvents()
        setResultListener()

        binding.textInputLoginEmail.setText(viewModel.state.value.email)
        binding.textInputLoginPassword.setText(viewModel.state.value.password)
    }

    private fun setResultListener() {
        parentFragmentManager.setFragmentResultListener(
            ForgotPasswordDialog.REQUEST_KEY,
            viewLifecycleOwner
        ) { _, bundle ->
            val email = bundle.getString(ForgotPasswordDialog.KEY_INPUT)
            email?.let { viewModel.onAction(LoginAction.ForgotPassword(it)) }
        }
    }

    private fun collectEvents() {
        viewLifecycleOwner.launchWhen(Lifecycle.State.STARTED) {
            viewModel.events.collect { event ->
                when (event) {
                    is LoginEvent.LoginError -> {
                        showErrorDialog(event.errorMessage)

                        Toast.makeText(requireContext(), event.errorMessage, Toast.LENGTH_SHORT)
                    }

                    LoginEvent.LoginSuccess -> {
                        Toast.makeText(requireContext(), "Login Success", Toast.LENGTH_SHORT)
                        navigateToHome()
                    }

                    is LoginEvent.ForgotPasswordSuccess -> {
                        showInfoDialog(
                            title = "Check your email",
                            message = "Mail sent to ${event.email}",
                        )
                    }
                }
            }
        }
    }

    private fun setListeners() {
        binding.buttonLoginLogin.setOnClickListener {
            viewModel.onAction(
                LoginAction.OnLoginClick(
                    email = binding.textInputLoginEmail.text.toString(),
                    password = binding.textInputLoginPassword.text.toString()
                )
            )
        }

        binding.textViewLoginForgotPassword.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_forgotPasswordDialog)
        }
    }

    private fun navigateToHome() {
        val request = NavDeepLinkRequest.Builder
            .fromUri(Deeplink.TO_HOME.link.toUri())
            .build()

        val navOptions = navOptions {
            popUpTo(R.id.nav_graph_auth) {
                inclusive = true
            }
        }
        findNavController().navigate(request, navOptions)
    }

    private fun setDontYouHaveAnAccountText() {
        binding.textViewLoginDontYouHaveAnAccount.setClickableSpan(
            fullText = getString(com.evirgenoguz.core.presentation.R.string.dont_you_have_an_account),
            clickablePart = getString(com.evirgenoguz.core.presentation.R.string.register),
            color = ContextCompat.getColor(
                requireContext(),
                com.evirgenoguz.core.presentation.R.color.colorPrimary
            )
        ) {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

}