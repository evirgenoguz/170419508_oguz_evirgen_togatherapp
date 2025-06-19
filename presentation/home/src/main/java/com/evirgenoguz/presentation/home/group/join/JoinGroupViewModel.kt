package com.evirgenoguz.presentation.home.group.join

import androidx.lifecycle.viewModelScope
import com.evirgenoguz.core.domain.util.Result
import com.evirgenoguz.core.presentation.base.BaseViewModel
import com.evirgenoguz.domain.usecase.home.JoinGroupUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JoinGroupViewModel @Inject constructor(
    private val joinGroupUseCase: JoinGroupUseCase
) : BaseViewModel() {

    private val eventChannel = Channel<JoinGroupEvent>()
    val events = eventChannel.receiveAsFlow()

    fun onAction(action: JoinGroupAction) {
        when (action) {
            is JoinGroupAction.OnJoinGroupClick -> joinGroup(action.inviteCode)
        }
    }

    private fun joinGroup(inviteCode: String) {
        viewModelScope.launch {
            showLoading()
            val result = joinGroupUseCase(inviteCode)
            when (result) {
                is Result.Error -> {
                    eventChannel.send(JoinGroupEvent.JoinGroupError(result.error.message))
                }

                is Result.Success -> eventChannel.send(JoinGroupEvent.JoinGroupSuccess)
            }
            hideLoading()
        }
    }
}