package com.evirgenoguz.presentation.profile.changelocation

sealed interface ChangeLocationEvent {
    data class Error(val message: String) : ChangeLocationEvent
    data object CountriesSuccess : ChangeLocationEvent
    data object CitiesSuccess : ChangeLocationEvent
    data object ChangeLocationSuccess : ChangeLocationEvent
}