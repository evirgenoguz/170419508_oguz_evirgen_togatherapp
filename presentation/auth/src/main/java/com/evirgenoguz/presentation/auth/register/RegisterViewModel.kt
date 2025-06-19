package com.evirgenoguz.presentation.auth.register

import androidx.lifecycle.viewModelScope
import com.evirgenoguz.core.domain.util.Result
import com.evirgenoguz.core.presentation.base.BaseViewModel
import com.evirgenoguz.domain.usecase.auth.RegisterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    val registerUseCase: RegisterUseCase
) : BaseViewModel() {

    private val _state = MutableStateFlow(RegisterUiState())
    val state = _state.asStateFlow()

    private val eventChannel = Channel<RegisterEvent>()
    val events = eventChannel.receiveAsFlow()
    fun onAction(action: RegisterAction) {
        when (action) {
            is RegisterAction.OnRegisterClick -> {
                _state.update {
                    it.copy(
                        email = action.email,
                        password = action.password,
                        confirmPassword = action.confirmPassword
                    )
                }
                register(
                    state.value.email,
                    state.value.password,
                    state.value.confirmPassword
                )
            }
        }
    }

    private fun register(
        email: String,
        password: String,
        confirmPassword: String
    ) {
        viewModelScope.launch {
            val result = registerUseCase(
                email = email,
                password = password,
                confirmPassword = confirmPassword
            )

            when (result) {
                is Result.Error -> {
                    eventChannel.send(RegisterEvent.Error(result.error.message))
                }

                is Result.Success -> {
                    eventChannel.send(RegisterEvent.RegisterSuccess)
                }
            }
        }
    }
}