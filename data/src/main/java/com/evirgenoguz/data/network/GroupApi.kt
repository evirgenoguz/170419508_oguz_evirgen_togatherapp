package com.evirgenoguz.data.network

import com.evirgenoguz.data.model.response.groupdetail.GroupMemberResponse
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface GroupApi {
    @POST("groups/leave")
    suspend fun leaveGroup(
        @Query("inviteCode") inviteCode: String
    ): Response<Unit>

    @GET("groups/members")
    suspend fun getGroupMembers(
        @Query("inviteCode") inviteCode: String
    ): Response<List<GroupMemberResponse>>

    @DELETE("api/groups/{inviteCode}/kick/{username}")
    suspend fun kickUserFromGroup(
        @Path("inviteCode") inviteCode: String,
        @Path("username") username: String
    ): Response<Unit>

    @DELETE("api/groups/{inviteCode}/kick/{username}")
    suspend fun closeGroup(
        @Query("inviteCode") inviteCode: String,
    ): Response<Unit>

}