package com.evirgenoguz.presentation.groupdetail

import androidx.lifecycle.viewModelScope
import com.evirgenoguz.core.domain.util.Result
import com.evirgenoguz.core.presentation.base.BaseViewModel
import com.evirgenoguz.domain.usecase.groupdetail.LeaveGroupUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroupDetailContainerViewModel @Inject constructor(
    private val leaveGroupUseCase: LeaveGroupUseCase
) : BaseViewModel() {

    private val eventChannel = Channel<GroupDetailContainerEvent>()
    val events = eventChannel.receiveAsFlow()
    fun onAction(action: GroupDetailContainerAction) {
        when (action) {
            is GroupDetailContainerAction.OnLeaveGroup -> leaveGroup(action.inviteCode)
        }
    }

    private fun leaveGroup(inviteCode: String) {
        viewModelScope.launch {
            showLoading()
            val result = leaveGroupUseCase.invoke(inviteCode)
            when (result) {
                is Result.Error -> showErrorDialog(result.error.message)
                is Result.Success -> eventChannel.send(GroupDetailContainerEvent.LeaveGroupSuccess)
            }
            hideLoading()
        }
    }
}