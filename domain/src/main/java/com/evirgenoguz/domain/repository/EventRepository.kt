package com.evirgenoguz.domain.repository

import com.evirgenoguz.core.domain.util.Result
import com.evirgenoguz.domain.model.groupdetail.CreateEventLocationDomainModel
import com.evirgenoguz.domain.model.groupdetail.CreateEventLocationReqDomainModel
import com.evirgenoguz.domain.model.groupdetail.CreateGroupEventDomainModel
import com.evirgenoguz.domain.model.groupdetail.GroupEventDomainModel
import com.evirgenoguz.domain.model.groupdetail.UpdateLiveLocationDomainModel

interface EventRepository {
    suspend fun createGroupEvent(createGroupEventDomainModel: CreateGroupEventDomainModel): Result<GroupEventDomainModel>

    suspend fun getGroupEvents(groupId: Int): Result<List<GroupEventDomainModel>>

    suspend fun getGroupEventDetail(eventId: Int): Result<GroupEventDomainModel>

    suspend fun getMyEvents(): Result<List<GroupEventDomainModel>>

    suspend fun createEventLocation(createEventLocationReqDomainModel: CreateEventLocationReqDomainModel): Result<CreateEventLocationDomainModel>

    suspend fun getCityIdByName(cityName: String): Result<Int>

    suspend fun getDistrictIdByName(districtName: String, cityId: Int): Result<Int>

    suspend fun joinEvent(eventId: Int): Result<Unit>
    
    suspend fun leaveEvent(eventId: Int): Result<Unit>

    suspend fun updateLocation(updateLiveLocationDomainModel: UpdateLiveLocationDomainModel): Result<Unit>

    suspend fun getLocationsByUsernames(usernames: List<String>): Result<List<UpdateLiveLocationDomainModel>>
}