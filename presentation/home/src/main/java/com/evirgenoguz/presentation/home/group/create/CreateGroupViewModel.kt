package com.evirgenoguz.presentation.home.group.create

import androidx.lifecycle.viewModelScope
import com.evirgenoguz.core.domain.util.Result
import com.evirgenoguz.core.presentation.base.BaseViewModel
import com.evirgenoguz.domain.model.home.CreateGroupDomainModel
import com.evirgenoguz.domain.usecase.home.CreateGroupUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateGroupViewModel @Inject constructor(
    private val createGroupUseCase: CreateGroupUseCase
) : BaseViewModel() {

    private val _state = MutableStateFlow(CreateGroupUiState())
    val state = _state.asStateFlow()

    private val eventChannel = Channel<CreateGroupEvent>()
    val events = eventChannel.receiveAsFlow()

    fun onAction(action: CreateGroupAction) {
        when (action) {
            is CreateGroupAction.OnCreateGroupClicked -> createGroup(
                action.name,
                action.description
            )
        }
    }

    private fun createGroup(name: String, description: String) {
        viewModelScope.launch {
            showLoading()
            val result = createGroupUseCase(
                createGroupDomainModel = CreateGroupDomainModel(
                    name = name,
                    description = description
                )
            )
            when (result) {
                is Result.Error -> {
                    eventChannel.send(CreateGroupEvent.Error(result.error.message))
                }

                is Result.Success -> {
                    eventChannel.send(CreateGroupEvent.CreateGroupSuccess)
                }
            }
            hideLoading()
        }
    }

}