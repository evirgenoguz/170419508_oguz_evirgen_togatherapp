package com.evirgenoguz.presentation.profile.changelocation

import androidx.lifecycle.viewModelScope
import com.evirgenoguz.core.domain.util.Result
import com.evirgenoguz.core.presentation.base.BaseViewModel
import com.evirgenoguz.domain.model.profile.ChangeLocationModel
import com.evirgenoguz.domain.usecase.profile.ChangeLocationUseCase
import com.evirgenoguz.domain.usecase.profile.LookupCitiesUseCase
import com.evirgenoguz.domain.usecase.profile.LookupCountriesUseCase
import com.evirgenoguz.domain.util.ClearableCache
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChangeLocationViewModel @Inject constructor(
    private val lookupCountriesUseCase: LookupCountriesUseCase,
    private val lookupCitiesUseCase: LookupCitiesUseCase,
    private val changeLocationUseCase: ChangeLocationUseCase,
    private val clearableCaches: Set<@JvmSuppressWildcards ClearableCache>
) : BaseViewModel() {

    private val _state = MutableStateFlow(ChangeLocationUiState())
    val state = _state.asStateFlow()

    private val eventChannel = Channel<ChangeLocationEvent>()
    val events = eventChannel.receiveAsFlow()


    fun onAction(action: ChangeLocationAction) {
        when (action) {
            ChangeLocationAction.LookupCountries -> lookupCountries()
            is ChangeLocationAction.LookupCities -> {
                _state.update {
                    _state.value.copy(
                        userLocation = _state.value.userLocation.copy(
                            countryCode = findCountryCode(
                                action.country
                            )
                        )
                    )
                }
                lookupCities(findCountryCode(action.country))
            }

            is ChangeLocationAction.UpdateCity -> {
                _state.update {
                    _state.value.copy(
                        userLocation = _state.value.userLocation.copy(
                            cityCode = findCityCode(
                                action.city
                            )
                        )
                    )
                }
            }

            ChangeLocationAction.ChangeLocation -> changeUserLocation()
        }
    }

    private fun findCountryCode(country: String): String {
        _state.value.countryList.forEach {
            if (it.name == country) {
                return it.code
            }
        }
        return ""
    }

    private fun findCityCode(city: String): String {
        _state.value.cityList.forEach {
            if (it.name == city) {
                return it.code
            }
        }
        return ""
    }

    private fun lookupCountries() {
        viewModelScope.launch {
            showLoading()
            val result = lookupCountriesUseCase()

            when (result) {
                is Result.Error -> {
                    eventChannel.send(ChangeLocationEvent.Error(result.error.message))
                }

                is Result.Success -> {
                    _state.update {
                        it.copy(countryList = result.data)
                    }
                    eventChannel.send(ChangeLocationEvent.CountriesSuccess)
                }
            }
            hideLoading()
        }
    }

    private fun lookupCities(code: String) {
        viewModelScope.launch {
            showLoading()
            val result = lookupCitiesUseCase(code)

            when (result) {
                is Result.Error -> {
                    eventChannel.send(ChangeLocationEvent.Error(result.error.message))
                }

                is Result.Success -> {
                    _state.update {
                        it.copy(cityList = result.data)
                    }
                    eventChannel.send(ChangeLocationEvent.CitiesSuccess)
                }
            }
            hideLoading()
        }
    }

    private fun changeUserLocation() {
        viewModelScope.launch {
            showLoading()
            val result = changeLocationUseCase(
                ChangeLocationModel(
                    cityCode = _state.value.userLocation.cityCode,
                    countryCode = _state.value.userLocation.countryCode,
                )
            )

            when (result) {
                is Result.Error -> {
                    eventChannel.send(ChangeLocationEvent.Error(result.error.message))
                }

                is Result.Success -> {
                    eventChannel.send(ChangeLocationEvent.ChangeLocationSuccess)
                    clearableCaches.forEach { it.clearAllCache() }
                }
            }
            hideLoading()
        }
    }

}