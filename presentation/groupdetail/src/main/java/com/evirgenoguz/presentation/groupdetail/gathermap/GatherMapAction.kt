package com.evirgenoguz.presentation.groupdetail.gathermap

sealed interface GatherMapAction {
    data class GetLocationsByUsernames(val usernames: List<String>) : GatherMapAction
}