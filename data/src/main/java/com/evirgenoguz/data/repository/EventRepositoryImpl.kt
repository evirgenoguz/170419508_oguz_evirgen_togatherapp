package com.evirgenoguz.data.repository

import com.evirgenoguz.core.domain.util.Result
import com.evirgenoguz.data.datasource.remote.EventRemoteDataSource
import com.evirgenoguz.data.model.request.groupdetail.toRequestModel
import com.evirgenoguz.domain.model.groupdetail.CreateEventLocationDomainModel
import com.evirgenoguz.domain.model.groupdetail.CreateEventLocationReqDomainModel
import com.evirgenoguz.domain.model.groupdetail.CreateGroupEventDomainModel
import com.evirgenoguz.domain.model.groupdetail.GroupEventDomainModel
import com.evirgenoguz.domain.model.groupdetail.UpdateLiveLocationDomainModel
import com.evirgenoguz.domain.repository.EventRepository
import javax.inject.Inject

class EventRepositoryImpl @Inject constructor(
    private val eventRemoteDataSource: EventRemoteDataSource
) : EventRepository {
    override suspend fun createGroupEvent(createGroupEventDomainModel: CreateGroupEventDomainModel): Result<GroupEventDomainModel> {
        return eventRemoteDataSource.createEvent(createGroupEventDomainModel.toRequestModel())
    }

    override suspend fun getGroupEvents(groupId: Int): Result<List<GroupEventDomainModel>> {
        return eventRemoteDataSource.getGroupEvents(groupId)
    }

    override suspend fun getGroupEventDetail(eventId: Int): Result<GroupEventDomainModel> {
        return eventRemoteDataSource.getGroupEventDetail(eventId)
    }

    override suspend fun getMyEvents(): Result<List<GroupEventDomainModel>> {
        return eventRemoteDataSource.getMyEvents()
    }

    override suspend fun createEventLocation(createEventLocationReqDomainModel: CreateEventLocationReqDomainModel): Result<CreateEventLocationDomainModel> {
        return eventRemoteDataSource.createEventLocation(createEventLocationReqDomainModel)
    }

    override suspend fun getCityIdByName(cityName: String): Result<Int> {
        return eventRemoteDataSource.getCityIdByName(cityName)
    }

    override suspend fun getDistrictIdByName(districtName: String, cityId: Int): Result<Int> {
        return eventRemoteDataSource.getDistrictIdByName(districtName, cityId)
    }

    override suspend fun joinEvent(eventId: Int): Result<Unit> {
        return eventRemoteDataSource.joinEvent(eventId)
    }

    override suspend fun leaveEvent(eventId: Int): Result<Unit> {
        return eventRemoteDataSource.leaveEvent(eventId)
    }

    override suspend fun updateLocation(updateLiveLocationDomainModel: UpdateLiveLocationDomainModel): Result<Unit> {
        return eventRemoteDataSource.updateLocation(updateLiveLocationDomainModel)
    }

    override suspend fun getLocationsByUsernames(usernames: List<String>): Result<List<UpdateLiveLocationDomainModel>> {
        return eventRemoteDataSource.getLocationsByUsernames(usernames)
    }
}