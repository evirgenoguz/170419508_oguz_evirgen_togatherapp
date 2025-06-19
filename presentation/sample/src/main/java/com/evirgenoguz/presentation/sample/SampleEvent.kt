package com.evirgenoguz.presentation.sample

sealed interface SampleEvent {

    data class Error(val errorMessage: String): SampleEvent

    data object DataSuccess: SampleEvent
}