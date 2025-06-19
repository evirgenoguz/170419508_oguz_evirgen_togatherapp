package com.evirgenoguz.presentation.groupdetail.creategroupevent

import androidx.lifecycle.viewModelScope
import com.evirgenoguz.core.presentation.base.BaseViewModel
import com.evirgenoguz.domain.model.groupdetail.CreateGroupEventDomainModel
import com.evirgenoguz.domain.usecase.groupdetail.CreateGroupEventUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.evirgenoguz.core.domain.util.Result
import com.evirgenoguz.domain.model.groupdetail.CreateEventLocationReqDomainModel
import com.evirgenoguz.domain.usecase.groupdetail.GetCityAndDistrictIdUseCase

@HiltViewModel
class CreateGroupEventViewModel @Inject constructor(
    private val createGroupEventUseCase: CreateGroupEventUseCase,
    private val getCityAndDistrictIdUseCase: GetCityAndDistrictIdUseCase
) : BaseViewModel() {

    private val _state = MutableStateFlow(CreateGroupEventState())
    val state = _state.asStateFlow()

    private val eventChannel = Channel<CreateGroupEventEvent>()
    val events = eventChannel.receiveAsFlow()

    fun onAction(action: CreateGroupEventAction) {
        when (action) {
            is CreateGroupEventAction.OnFullAddressChanged -> {
                _state.value = _state.value.copy(
                    fullAddress = action.fullAddress,
                    cityName = action.cityName,
                    districtName = action.districtName
                )
                getCityAndDistrictIdByName()
            }

            is CreateGroupEventAction.OnSelectedLocationChanged -> {
                _state.value = _state.value.copy(locationModel = action.locationModel)
            }

            is CreateGroupEventAction.OnCreateEventClicked -> createGroupEvent(
                action.createEventModel,
                action.createEventLocationModel.copy(

                )
            )
        }
    }

    private fun getCityAndDistrictIdByName() {
        viewModelScope.launch {
            val result = getCityAndDistrictIdUseCase(
                _state.value.cityName.orEmpty(),
                _state.value.districtName.orEmpty()
            )

            when (result) {
                is Result.Error -> {}
                is Result.Success -> {
                    _state.value = _state.value.copy(
                        cityId = result.data.first,
                        districtId = result.data.second
                    )
                }
            }
        }
    }

    private fun createGroupEvent(
        createGroupEventDomainModel: CreateGroupEventDomainModel,
        createGroupEventLocationReqDomainModel: CreateEventLocationReqDomainModel
    ) {
        viewModelScope.launch {
            showLoading()
            val result = createGroupEventUseCase(
                createGroupEventDomainModel = createGroupEventDomainModel,
                createGroupEventLocationReqDomainModel = createGroupEventLocationReqDomainModel
            )
            when (result) {
                is Result.Error -> {
                    eventChannel.send(CreateGroupEventEvent.Error(result.error.message))
                }

                is Result.Success -> {
                    eventChannel.send(CreateGroupEventEvent.CreateEventSuccess)
                }
            }
            hideLoading()
        }
    }
}