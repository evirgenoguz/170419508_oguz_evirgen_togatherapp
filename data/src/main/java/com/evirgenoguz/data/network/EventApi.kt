package com.evirgenoguz.data.network

import com.evirgenoguz.data.model.request.groupdetail.CreateEventLocationRequest
import com.evirgenoguz.data.model.request.groupdetail.CreateEventRequest
import com.evirgenoguz.data.model.request.groupdetail.UpdateLiveLocationRequest
import com.evirgenoguz.data.model.response.groupdetail.CreateEventLocationResponse
import com.evirgenoguz.data.model.response.groupdetail.GetCityIdResponse
import com.evirgenoguz.data.model.response.groupdetail.GetDistrictIdResponse
import com.evirgenoguz.data.model.response.groupdetail.GroupEventResponse
import com.evirgenoguz.data.model.response.groupdetail.LiveLocationResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface EventApi {
    @POST("events/create")
    suspend fun createEvent(
        @Body createEventRequest: CreateEventRequest
    ): Response<GroupEventResponse>

    @GET("events/group-events")
    suspend fun getGroupEvents(
        @Query("groupId") groupId: Int
    ): Response<List<GroupEventResponse>>

    @GET("events/get")
    suspend fun getGroupEventDetail(
        @Query("eventId") eventId: Int
    ): Response<GroupEventResponse>

    @GET("events/my-events")
    suspend fun getMyEvents(
        @Query("status") status: String = "PENDING"
    ): Response<List<GroupEventResponse>>

    @POST("events/location/create")
    suspend fun createEventLocation(
        @Body createEventLocationRequest: CreateEventLocationRequest
    ): Response<CreateEventLocationResponse>

    @GET("cities/by-name")
    suspend fun getCityIdByName(
        @Query("name") cityName: String
    ): Response<GetCityIdResponse>

    @GET("districts/by-name")
    suspend fun getDistrictsIdByName(
        @Query("name") districtName: String,
        @Query("cityId") cityId: Int
    ): Response<GetDistrictIdResponse>

    @POST("events/leave")
    suspend fun leaveEvent(
        @Query("eventId") eventId: Int
    ): Response<Unit>

    @POST("events/join")
    suspend fun joinEvent(
        @Query("eventId") eventId: Int
    ): Response<Unit>

    @POST("user-location/update")
    suspend fun updateLocation(
        @Body updateLocationRequest: UpdateLiveLocationRequest
    ): Response<UpdateLiveLocationRequest>

    @POST("user-location/get-by-usernames")
    suspend fun getLocationsByUsernames(
        @Body usernames: List<String>
    ): Response<List<LiveLocationResponse>>
}