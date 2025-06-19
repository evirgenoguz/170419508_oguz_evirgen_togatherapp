package com.evirgenoguz.presentation.groupdetail.events

import androidx.lifecycle.viewModelScope
import com.evirgenoguz.core.domain.util.Result
import com.evirgenoguz.core.presentation.base.BaseViewModel
import com.evirgenoguz.domain.usecase.groupdetail.GetGroupEventsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroupDetailEventsViewModel @Inject constructor(
    private val getGroupEventsUseCase: GetGroupEventsUseCase
) : BaseViewModel() {

    private val _state = MutableStateFlow(GroupDetailState())
    val state = _state.asStateFlow()

    private val eventChannel = Channel<GroupDetailEventsEvent>()
    val events = eventChannel.receiveAsFlow()

    fun onAction(action: GroupDetailEventsAction) {
        when (action) {
            is GroupDetailEventsAction.GetGroupEvents -> getGroupEvents(action.groupId)
        }
    }

    private fun getGroupEvents(groupId: Int) {
        viewModelScope.launch {
            showLoading()
            val result = getGroupEventsUseCase(groupId)
            when (result) {
                is Result.Error -> {
                    eventChannel.send(GroupDetailEventsEvent.Error(result.error.message))
                }
                is Result.Success -> {
                    _state.value = state.value.copy(groupEventList = result.data)
                    eventChannel.send(GroupDetailEventsEvent.GroupEventsSuccess)
                }
            }
            hideLoading()
        }
    }
}