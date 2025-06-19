package com.evirgenoguz.presentation.foryou

sealed interface ForYouAction {
    data class GetCityIdAndGetCampaigns(val city: String?) : ForYouAction

}