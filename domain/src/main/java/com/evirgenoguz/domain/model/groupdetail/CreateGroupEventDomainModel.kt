package com.evirgenoguz.domain.model.groupdetail

import java.time.LocalDateTime

data class CreateGroupEventDomainModel(
    val name: String,
    val description: String,
    val startDate: LocalDateTime,
    val endDate: LocalDateTime,
    val eventLocationId: Int? = null,
    val createdByGroupId: Int,
    val eventCategory: String,
    val totalParticipants: Int = 1
)