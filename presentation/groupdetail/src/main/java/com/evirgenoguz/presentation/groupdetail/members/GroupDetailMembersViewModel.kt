package com.evirgenoguz.presentation.groupdetail.members

import androidx.lifecycle.viewModelScope
import com.evirgenoguz.core.domain.util.Result
import com.evirgenoguz.core.presentation.base.BaseViewModel
import com.evirgenoguz.domain.usecase.groupdetail.CloseGroupUseCase
import com.evirgenoguz.domain.usecase.groupdetail.GetGroupMembersUseCase
import com.evirgenoguz.domain.usecase.groupdetail.KickUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroupDetailMembersViewModel @Inject constructor(
    private val getGroupMembersUseCase: GetGroupMembersUseCase,
    private val kickUserUseCase: KickUserUseCase,
    private val closeGroupUseCase: CloseGroupUseCase
) : BaseViewModel() {

    private val _state = MutableStateFlow(GroupDetailMemberState())
    val state = _state.asStateFlow()

    private val eventChannel = Channel<GroupDetailMembersEvent>()
    val events = eventChannel.receiveAsFlow()

    fun onAction(action: GroupDetailMembersAction) {
        when (action) {
            is GroupDetailMembersAction.OnKickUserClicked -> kickUser(action.inviteCode, action.username)
            is GroupDetailMembersAction.GetGroupMembers -> getGroupMembers(action.inviteCode)
            is GroupDetailMembersAction.OnDeleteGroupClicked -> closeGroup(action.inviteCode)
        }
    }

    private fun closeGroup(inviteCode: String) {
        viewModelScope.launch {
            showLoading()
            val result = closeGroupUseCase(inviteCode)

            when (result) {
                is Result.Error -> eventChannel.send(GroupDetailMembersEvent.Error(result.error.message))
                is Result.Success -> eventChannel.send(GroupDetailMembersEvent.CloseGroupSuccess)
            }
            hideLoading()
        }
    }


    private fun kickUser(inviteCode: String, username: String) {
        viewModelScope.launch {
            showLoading()
            val result = kickUserUseCase(inviteCode, username)
            when (result) {
                is Result.Error -> eventChannel.send(GroupDetailMembersEvent.Error(result.error.message))
                is Result.Success -> eventChannel.send(GroupDetailMembersEvent.KickUserSuccess)
            }
            hideLoading()
        }
    }

    private fun getGroupMembers(inviteCode: String) {
        viewModelScope.launch {
            showLoading()
            val result = getGroupMembersUseCase.invoke(inviteCode)

            when (result) {
                is Result.Error -> eventChannel.send(GroupDetailMembersEvent.Error(result.error.message))
                is Result.Success -> {
                    _state.value = _state.value.copy(
                        members = result.data
                    )
                    eventChannel.send(GroupDetailMembersEvent.GroupMembersDataSuccess)
                }
            }
            hideLoading()
        }
    }

}