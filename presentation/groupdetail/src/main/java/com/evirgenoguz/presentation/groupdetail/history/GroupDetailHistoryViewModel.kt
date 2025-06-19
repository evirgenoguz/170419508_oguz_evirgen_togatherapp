package com.evirgenoguz.presentation.groupdetail.history

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
class GroupDetailHistoryViewModel @Inject constructor(
    private val getGroupEventsUseCase: GetGroupEventsUseCase
) : BaseViewModel() {
    private val _state = MutableStateFlow(GroupDetailHistoryState())
    val state = _state.asStateFlow()

    private val eventChannel = Channel<GroupDetailHistoryEvent>()
    val events = eventChannel.receiveAsFlow()

    fun onAction(action: GroupDetailHistoryAction) {
        when (action) {
            is GroupDetailHistoryAction.GetGroupEventsHistoryEvent -> getGroupEventsAndTakePast(
                action.groupId
            )
        }
    }

    private fun getGroupEventsAndTakePast(groupId: Int) {
        viewModelScope.launch {
            val result = getGroupEventsUseCase(groupId)
            showLoading()
            when (result) {
                is Result.Error -> eventChannel.send(GroupDetailHistoryEvent.Error(result.error.message))
                is Result.Success -> {
                    val pastEventList = result.data.filter { it.eventStatus == "PAST" }
                    _state.value = _state.value.copy(groupEventDomainModelList = pastEventList)
                    eventChannel.send(GroupDetailHistoryEvent.GroupEventsHistorySuccess)
                }
            }
            hideLoading()
        }
    }
}