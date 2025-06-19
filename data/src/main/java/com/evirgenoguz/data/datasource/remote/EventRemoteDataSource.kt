package com.evirgenoguz.data.datasource.remote

import com.evirgenoguz.core.data.networking.safeCall
import com.evirgenoguz.core.domain.util.Result
import com.evirgenoguz.data.model.request.groupdetail.CreateEventRequest
import com.evirgenoguz.data.model.request.groupdetail.toDomainModel
import com.evirgenoguz.data.model.request.groupdetail.toRequestModel
import com.evirgenoguz.data.model.response.groupdetail.toDomainModel
import com.evirgenoguz.data.network.EventApi
import com.evirgenoguz.domain.model.groupdetail.CreateEventLocationDomainModel
import com.evirgenoguz.domain.model.groupdetail.CreateEventLocationReqDomainModel
import com.evirgenoguz.domain.model.groupdetail.GroupEventDomainModel
import com.evirgenoguz.domain.model.groupdetail.UpdateLiveLocationDomainModel


class EventRemoteDataSource(
    private val eventApi: EventApi
) {
    suspend fun createEvent(createEventRequest: CreateEventRequest): Result<GroupEventDomainModel> {
        val result = safeCall {
            eventApi.createEvent(createEventRequest)
        }

        return when (result) {
            is Result.Error -> Result.Error(result.error)
            is Result.Success -> Result.Success(result.data.toDomainModel())
        }
    }

    suspend fun getGroupEvents(groupId: Int): Result<List<GroupEventDomainModel>> {
        val result = safeCall {
            eventApi.getGroupEvents(groupId)
        }

        return when (result) {
            is Result.Error -> Result.Error(result.error)
            is Result.Success -> Result.Success(result.data.toDomainModel())
        }
    }

    suspend fun getGroupEventDetail(eventId: Int): Result<GroupEventDomainModel> {
        val result = safeCall {
            eventApi.getGroupEventDetail(eventId)
        }

        return when (result) {
            is Result.Error -> Result.Error(result.error)
            is Result.Success -> Result.Success(result.data.toDomainModel())
        }
    }

    suspend fun getMyEvents(): Result<List<GroupEventDomainModel>> {
        val result = safeCall {
            eventApi.getMyEvents()
        }

        return when (result) {
            is Result.Error -> Result.Error(result.error)
            is Result.Success -> Result.Success(result.data.toDomainModel())
        }
    }

    suspend fun createEventLocation(createEventLocation: CreateEventLocationReqDomainModel): Result<CreateEventLocationDomainModel> {
        val result = safeCall {
            eventApi.createEventLocation(createEventLocation.toRequestModel())
        }

        return when (result) {
            is Result.Error -> Result.Error(result.error)
            is Result.Success -> Result.Success(result.data.toDomainModel())
        }
    }

    suspend fun getCityIdByName(cityName: String): Result<Int> {
        val result = safeCall {
            eventApi.getCityIdByName(cityName)
        }

        return when (result) {
            is Result.Error -> Result.Error(result.error)
            is Result.Success -> Result.Success(result.data.id ?: -1)
        }
    }

    suspend fun getDistrictIdByName(districtName: String, cityId: Int): Result<Int> {
        val result = safeCall {
            eventApi.getDistrictsIdByName(districtName, cityId)
        }

        return when (result) {
            is Result.Error -> Result.Error(result.error)
            is Result.Success -> Result.Success(result.data.id ?: -1)
        }
    }

    suspend fun joinEvent(eventId: Int): Result<Unit> {
        val result = safeCall {
            eventApi.joinEvent(eventId)
        }
        return when (result) {
            is Result.Error -> Result.Error(result.error)
            is Result.Success -> Result.Success(Unit)
        }
    }

    suspend fun leaveEvent(eventId: Int): Result<Unit> {
        val result = safeCall {
            eventApi.leaveEvent(eventId)
        }
        return when (result) {
            is Result.Error -> Result.Error(result.error)
            is Result.Success -> Result.Success(Unit)
        }
    }

    suspend fun updateLocation(updateLocationDomainModel: UpdateLiveLocationDomainModel): Result<Unit> {
        val result = safeCall {
            eventApi.updateLocation(updateLocationDomainModel.toRequestModel())
        }

        return when (result) {
            is Result.Error -> Result.Error(result.error)
            is Result.Success -> Result.Success(Unit)
        }
    }

    suspend fun getLocationsByUsernames(usernames: List<String>): Result<List<UpdateLiveLocationDomainModel>> {
        val result = safeCall {
            eventApi.getLocationsByUsernames(usernames)
        }
        return when (result) {
            is Result.Error -> Result.Error(result.error)
            is Result.Success -> Result.Success(result.data.toDomainModel())
        }
    }
}