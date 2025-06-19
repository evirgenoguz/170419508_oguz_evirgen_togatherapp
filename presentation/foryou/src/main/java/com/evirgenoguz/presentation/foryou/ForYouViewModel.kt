package com.evirgenoguz.presentation.foryou

import androidx.lifecycle.viewModelScope
import com.evirgenoguz.core.domain.util.Result
import com.evirgenoguz.core.presentation.base.BaseViewModel
import com.evirgenoguz.domain.usecase.campaign.GetCampaignUseCase
import com.evirgenoguz.domain.usecase.campaign.GetCityIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForYouViewModel @Inject constructor(
    private val getCampaignUseCase: GetCampaignUseCase,
    private val getCityIdUseCase: GetCityIdUseCase
) : BaseViewModel() {

    private val _state = MutableStateFlow(ForYouState())
    val state = _state.asStateFlow()

    private val eventChannel = Channel<ForYouEvent>()
    val events = eventChannel.receiveAsFlow()

    fun onAction(action: ForYouAction) {
        when (action) {
            is ForYouAction.GetCityIdAndGetCampaigns -> getCityId(action.city)
        }
    }

    private fun getCityId(cityName: String?) {
        cityName ?: return
        viewModelScope.launch {
            val result = getCityIdUseCase.invoke(cityName)
            when (result) {
                is Result.Error -> eventChannel.send(ForYouEvent.Error(result.error.message))
                is Result.Success -> getCampaignByCityId(result.data)
            }
        }
    }

    private fun getCampaignByCityId(cityId: Int) {
        viewModelScope.launch {
            showLoading()
            val result = getCampaignUseCase.invoke(cityId)

            when (result) {
                is Result.Error -> eventChannel.send(ForYouEvent.Error(result.error.message))
                is Result.Success -> {
                    _state.value = _state.value.copy(
                        campaignList = result.data
                    )
                    eventChannel.send(ForYouEvent.CampaignsDataSuccess)
                }
            }

            hideLoading()
        }
    }
}