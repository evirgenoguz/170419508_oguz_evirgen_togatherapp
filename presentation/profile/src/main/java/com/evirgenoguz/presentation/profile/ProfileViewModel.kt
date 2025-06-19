package com.evirgenoguz.presentation.profile

import androidx.lifecycle.viewModelScope
import com.evirgenoguz.core.domain.util.Result
import com.evirgenoguz.core.presentation.base.BaseViewModel
import com.evirgenoguz.domain.model.profile.ChangePasswordModel
import com.evirgenoguz.domain.usecase.profile.ChangePasswordUseCase
import com.evirgenoguz.domain.usecase.profile.GetProfileInformationUseCase
import com.evirgenoguz.domain.usecase.profile.LogOutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getProfileInformationUseCase: GetProfileInformationUseCase,
    private val logOutUseCase: LogOutUseCase,
    private val changePasswordUseCase: ChangePasswordUseCase,
) : BaseViewModel() {

    private val _state = MutableStateFlow(ProfileUiState())
    val state = _state.asStateFlow()

    private val eventChannel = Channel<ProfileEvent>()
    val events = eventChannel.receiveAsFlow()

    fun onAction(action: ProfileAction) {
        when (action) {
            ProfileAction.OnLogoutClick -> logout()
            is ProfileAction.OnChangePasswordClick -> changePassword(action.changePasswordModel)
        }
    }

    private fun logout() {
        viewModelScope.launch {
            val result = logOutUseCase.invoke()
            when (result) {
                is Result.Success -> {
                    eventChannel.send(ProfileEvent.LogoutSuccess)
                }

                else -> Unit
            }
        }
    }

    fun getUserInformation() {
        viewModelScope.launch {
            showLoading()
            val result = getProfileInformationUseCase.invoke()
            when (result) {
                is Result.Error -> {
                    eventChannel.send(ProfileEvent.Error(result.error.message))
                }

                is Result.Success -> {
                    _state.value = _state.value.copy(
                        userModel = result.data
                    )
                    eventChannel.send(ProfileEvent.DataSuccess)
                }
            }
            hideLoading()
        }
    }

    private fun changePassword(changePasswordModel: ChangePasswordModel) {
        viewModelScope.launch {
            showLoading()
            val result = changePasswordUseCase.invoke(changePasswordModel)

            when (result) {
                is Result.Error -> {
                    eventChannel.send(ProfileEvent.ChangePasswordError(result.error.message))
                }

                is Result.Success -> {
                    eventChannel.send(ProfileEvent.ChangePasswordSuccess)
                }
            }
            hideLoading()
        }
    }

}