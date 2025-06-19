package com.evirgenoguz.presentation.auth.login

import androidx.lifecycle.viewModelScope
import com.evirgenoguz.core.domain.util.Result
import com.evirgenoguz.core.presentation.base.BaseViewModel
import com.evirgenoguz.domain.usecase.auth.ForgotPasswordUseCase
import com.evirgenoguz.domain.usecase.auth.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val forgotPasswordUseCase: ForgotPasswordUseCase
) : BaseViewModel() {

    private val _state = MutableStateFlow(LoginState())
    val state = _state.asStateFlow()

    private val eventChannel = Channel<LoginEvent>()
    val events = eventChannel.receiveAsFlow()

    fun onAction(action: LoginAction) {
        when (action) {
            is LoginAction.OnLoginClick -> login(action.email, action.password)
            is LoginAction.ForgotPassword -> forgotPassword(action.email)
            else -> {}
        }
    }

    private fun forgotPassword(email: String) {
        viewModelScope.launch {
            showLoading()
            val result = forgotPasswordUseCase(email)
            when (result) {
                is Result.Error -> eventChannel.send(LoginEvent.LoginError(result.error.message))
                is Result.Success -> eventChannel.send(LoginEvent.ForgotPasswordSuccess(email))
            }
            hideLoading()
        }
    }

    private fun login(email: String, password: String) {
        viewModelScope.launch {
            showLoading()
            val result = loginUseCase(email, password)
            when (result) {
                is Result.Error -> {
                    eventChannel.send(LoginEvent.LoginError(result.error.message))
                }

                is Result.Success -> {
                    eventChannel.send(LoginEvent.LoginSuccess)
                }
            }
            hideLoading()
        }
    }
}