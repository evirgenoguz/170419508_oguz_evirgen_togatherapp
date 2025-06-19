package com.evirgenoguz.data.model.response

data class UserEventResponse(
    private val id: Int,
    private val eventList: List<EventResponse>
)
