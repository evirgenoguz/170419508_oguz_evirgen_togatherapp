package com.evirgenoguz.data.network

import com.evirgenoguz.data.model.request.home.CreateGroupRequest
import com.evirgenoguz.data.model.response.home.GroupResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface HomeApi {
    @GET("groups/myGroups")
    suspend fun getGroups(): Response<List<GroupResponse>>

    @POST("groups/create")
    suspend fun createGroup(
        @Body createGroupRequest: CreateGroupRequest
    ): Response<GroupResponse>

    @POST("groups/join")
    suspend fun joinGroup(
        @Query("inviteCode") inviteCode: String
    ): Response<Unit>
}