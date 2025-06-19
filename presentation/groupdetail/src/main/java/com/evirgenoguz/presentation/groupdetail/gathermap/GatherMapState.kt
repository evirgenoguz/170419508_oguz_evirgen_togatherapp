package com.evirgenoguz.presentation.groupdetail.gathermap

import com.evirgenoguz.domain.model.groupdetail.UpdateLiveLocationDomainModel

data class GatherMapState(
    val participants: List<String> = emptyList(),
    var locations: List<UpdateLiveLocationDomainModel> = emptyList()
)
