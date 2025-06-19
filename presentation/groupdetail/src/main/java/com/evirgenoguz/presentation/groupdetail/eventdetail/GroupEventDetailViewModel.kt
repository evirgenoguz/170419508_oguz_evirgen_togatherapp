package com.evirgenoguz.presentation.groupdetail.eventdetail

import androidx.lifecycle.viewModelScope
import com.evirgenoguz.core.domain.util.Result
import com.evirgenoguz.core.presentation.base.BaseViewModel
import com.evirgenoguz.domain.usecase.groupdetail.GroupEventDetailUseCase
import com.evirgenoguz.domain.usecase.groupdetail.JoinGroupEventUseCase
import com.evirgenoguz.domain.usecase.groupdetail.LeaveGroupEventUseCase
import com.evirgenoguz.domain.util.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroupEventDetailViewModel @Inject constructor(
    private val groupEventDetailUseCase: GroupEventDetailUseCase,
    private val joinGroupEventUseCase: JoinGroupEventUseCase,
    private val leaveGroupEventUseCase: LeaveGroupEventUseCase
) : BaseViewModel() {

    private val _state = MutableStateFlow(GroupEventDetailState())
    val state = _state.asStateFlow()

    private val eventChannel = Channel<GroupEventDetailEvent>()
    val events = eventChannel.receiveAsFlow()

    fun onAction(action: GroupEventDetailAction) {
        when (action) {
            is GroupEventDetailAction.GetGroupEventDetail -> getGroupEventDetail(action.eventId)
            is GroupEventDetailAction.JoinGroupEvent -> joinGroupEvent(action.eventId)
            is GroupEventDetailAction.LeaveGroupEvent -> leaveGroupEvent(action.eventId)
            is GroupEventDetailAction.ChangeJoinState -> changeJoinState(action.joinState)
        }

    }

    private fun changeJoinState(joinState: Boolean) {
        viewModelScope.launch {
            _state.value = _state.value.copy(alreadyJoined = joinState)
            eventChannel.send(GroupEventDetailEvent.ChangedJoinState)
        }
    }

    private fun leaveGroupEvent(eventId: Int) {
        viewModelScope.launch {
            showLoading()
            val result = leaveGroupEventUseCase(eventId)

            when (result) {
                is Result.Error -> eventChannel.send(GroupEventDetailEvent.Error(result.error.message))
                is Result.Success -> {
                    eventChannel.send(GroupEventDetailEvent.JoinGroupEventSuccess)
                }
            }

            hideLoading()
            hideLoading()
        }
    }

    private fun joinGroupEvent(eventId: Int) {
        viewModelScope.launch {
            showLoading()
            val result = joinGroupEventUseCase(eventId)

            when (result) {
                is Result.Error -> eventChannel.send(GroupEventDetailEvent.Error(result.error.message))
                is Result.Success -> {
                    eventChannel.send(GroupEventDetailEvent.JoinGroupEventSuccess)
                }
            }

            hideLoading()
        }
    }

    private fun getGroupEventDetail(eventId: Int) {
        viewModelScope.launch {
            showLoading()
            val result = groupEventDetailUseCase(eventId)

            when (result) {
                is Result.Error -> eventChannel.send(GroupEventDetailEvent.Error(result.error.message))
                is Result.Success -> {
                    _state.value = _state.value.copy(groupEventDomainModel = result.data)
                    checkAlreadyJoined()
                    eventChannel.send(GroupEventDetailEvent.GroupEventDetailSuccess(result.data))
                }
            }

            hideLoading()
        }
    }

    private fun checkAlreadyJoined() {
        val isJoined = _state.value.groupEventDomainModel
            ?.participants
            ?.any { it.user?.username == SessionManager.getCurrentUser()?.username } == true

        _state.value = _state.value.copy(alreadyJoined = isJoined)
    }
}