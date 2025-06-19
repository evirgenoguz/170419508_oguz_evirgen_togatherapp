package com.evirgenoguz.presentation.foryou

sealed interface ForYouEvent {
    data class Error(val message: String) : ForYouEvent
    data object CampaignsDataSuccess : ForYouEvent
}