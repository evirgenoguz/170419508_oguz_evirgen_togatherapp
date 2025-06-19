package com.evirgenoguz.presentation.profile.changelocation

sealed interface ChangeLocationAction {
    data object LookupCountries : ChangeLocationAction
    data class LookupCities(val country: String) : ChangeLocationAction
    data class UpdateCity(val city: String) : ChangeLocationAction
    data object ChangeLocation : ChangeLocationAction
}