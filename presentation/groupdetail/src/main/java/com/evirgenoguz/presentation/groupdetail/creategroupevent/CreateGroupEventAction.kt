package com.evirgenoguz.presentation.groupdetail.creategroupevent

import com.evirgenoguz.domain.model.groupdetail.CreateEventLocationReqDomainModel
import com.evirgenoguz.domain.model.groupdetail.CreateGroupEventDomainModel
import com.evirgenoguz.location.LocationModel

sealed interface CreateGroupEventAction {
    data class OnFullAddressChanged(
        val fullAddress: String,
        val cityName: String,
        val districtName: String
    ) : CreateGroupEventAction

    data class OnSelectedLocationChanged(val locationModel: LocationModel) : CreateGroupEventAction
    data class OnCreateEventClicked(
        val createEventModel: CreateGroupEventDomainModel,
        val createEventLocationModel: CreateEventLocationReqDomainModel
    ) : CreateGroupEventAction
}