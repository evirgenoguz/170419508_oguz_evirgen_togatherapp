package com.evirgenoguz.presentation.groupdetail.gathermap

sealed interface GatherMapEvent {
    data object LocationsSuccess : GatherMapEvent
}