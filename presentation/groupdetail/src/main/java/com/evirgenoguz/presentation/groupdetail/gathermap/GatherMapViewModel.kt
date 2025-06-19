package com.evirgenoguz.presentation.groupdetail.gathermap

import androidx.lifecycle.viewModelScope
import com.evirgenoguz.core.domain.util.Result
import com.evirgenoguz.core.presentation.base.BaseViewModel
import com.evirgenoguz.domain.usecase.groupdetail.GetLocationsByUsernamesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GatherMapViewModel @Inject constructor(
    private val getLocationsByUsernamesUseCase: GetLocationsByUsernamesUseCase
) : BaseViewModel() {

    private val _state = MutableStateFlow(GatherMapState())
    val state = _state.asStateFlow()

    private val eventChannel = Channel<GatherMapEvent>()
    val events = eventChannel.receiveAsFlow()

    fun onAction(action: GatherMapAction) {
        when (action) {
            is GatherMapAction.GetLocationsByUsernames -> getLocations(action.usernames)
        }
    }

    private fun getLocations(usernames: List<String>) {
        viewModelScope.launch {
            val result = getLocationsByUsernamesUseCase.invoke(usernames)

            when (result) {
                is Result.Error -> {}
                is Result.Success -> {
                    _state.value = _state.value.copy(
                        locations = result.data
                    )
                    eventChannel.send(GatherMapEvent.LocationsSuccess)
                }
            }
        }
    }
}