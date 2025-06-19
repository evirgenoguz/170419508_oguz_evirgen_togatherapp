package com.evirgenoguz.presentation.home.home.state

sealed interface HomeEvent {
    data class Error(val errorMessage: String) : HomeEvent
    data object GroupsDataSuccess : HomeEvent
    data object UserDataSuccess : HomeEvent
    data object MyEventsSuccess: HomeEvent
}