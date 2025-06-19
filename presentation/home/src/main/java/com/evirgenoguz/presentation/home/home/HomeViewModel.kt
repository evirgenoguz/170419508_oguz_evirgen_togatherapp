package com.evirgenoguz.presentation.home.home

import androidx.lifecycle.viewModelScope
import com.evirgenoguz.core.domain.util.Result
import com.evirgenoguz.core.presentation.base.BaseViewModel
import com.evirgenoguz.domain.usecase.home.GetMyEventsUseCase
import com.evirgenoguz.domain.usecase.home.GetUserGroupsUseCase
import com.evirgenoguz.domain.usecase.profile.GetProfileInformationUseCase
import com.evirgenoguz.domain.util.SessionManager
import com.evirgenoguz.presentation.home.home.state.HomeAction
import com.evirgenoguz.presentation.home.home.state.HomeEvent
import com.evirgenoguz.presentation.home.home.state.HomeUiState
import com.evirgenoguz.presentation.home.home.state.SelectedType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getUserGroupsUseCase: GetUserGroupsUseCase,
    private val getProfileInformationUseCase: GetProfileInformationUseCase,
    private val getMyEventsUseCase: GetMyEventsUseCase
) : BaseViewModel() {

    private val _state = MutableStateFlow(HomeUiState.initial())
    val state = _state.asStateFlow()

    private val eventChannel = Channel<HomeEvent>()
    val events = eventChannel.receiveAsFlow()

    fun onAction(action: HomeAction) {
        when (action) {
            HomeAction.OnEventButtonClick -> {
                _state.update {
                    it.copy(selectedType = SelectedType.Event)
                }
            }

            HomeAction.OnGroupButtonClick -> {
                _state.update {
                    it.copy(selectedType = SelectedType.Group)
                }
            }
        }
    }

    fun getGroups() {
        viewModelScope.launch {
            showLoading()
            val result = getUserGroupsUseCase.invoke()
            when (result) {
                is Result.Error -> {
                    eventChannel.send(HomeEvent.Error(result.error.message))
                }

                is Result.Success -> {
                    _state.update { it.copy(userGroupList = result.data) }
                    eventChannel.send(HomeEvent.GroupsDataSuccess)
                }
            }
            hideLoading()
        }
    }

    fun getMyEvents() {
        viewModelScope.launch {
            showLoading()
            val result = getMyEventsUseCase.invoke()

            when (result) {
                is Result.Error -> eventChannel.send(HomeEvent.Error(result.error.message))
                is Result.Success -> {
                    _state.update { it.copy(userEventList = result.data) }
                    eventChannel.send(HomeEvent.MyEventsSuccess)
                }
            }
            hideLoading()
        }
    }

    fun getUserInformation() {
        viewModelScope.launch {
            if (SessionManager.getCurrentUser() == null) {
                val result = getProfileInformationUseCase.invoke()
                when (result) {
                    is Result.Error -> {
                        eventChannel.send(HomeEvent.Error(result.error.message))
                    }

                    is Result.Success -> {
                        SessionManager.updateUser(result.data)
                        _state.value = _state.value.copy(
                            userModel = result.data
                        )
                        eventChannel.send(HomeEvent.UserDataSuccess)
                    }
                }
            }
        }
    }
}