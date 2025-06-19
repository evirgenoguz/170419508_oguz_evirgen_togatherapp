package com.evirgenoguz.data.network

import com.evirgenoguz.data.model.request.profile.ChangePasswordRequest
import com.evirgenoguz.data.model.request.profile.UpdateLocationRequest
import com.evirgenoguz.data.model.request.profile.UpdateProfileRequestModel
import com.evirgenoguz.data.model.response.profile.CityLookupResponse
import com.evirgenoguz.data.model.response.profile.CountryLookupResponse
import com.evirgenoguz.data.model.response.profile.DistrictLookupResponse
import com.evirgenoguz.data.model.response.profile.ProfileInformationResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Query

interface ProfileApi {
    @GET("users/profile")
    suspend fun getProfile(): Response<ProfileInformationResponse>

    @PUT("users/profile")
    suspend fun updateUserProfile(
        @Body updateProfileRequest: UpdateProfileRequestModel
    ): Response<ProfileInformationResponse>

    @PUT("users/profile/password")
    suspend fun changePassword(
        @Body changePasswordRequest: ChangePasswordRequest
    ): Response<Unit>

    @GET("countries/lookup")
    suspend fun lookupCountries(): Response<List<CountryLookupResponse>>

    @GET("cities/lookup")
    suspend fun lookupCities(
        @Query("code") code: String
    ): Response<List<CityLookupResponse>>

    @GET("districts/lookup")
    suspend fun lookupDistricts(
        @Query("cityCode") cityCode: String
    ): Response<List<DistrictLookupResponse>>

    @PUT("users/profile/UpdateCity")
    suspend fun updateLocation(
        @Body updateCityRequest: UpdateLocationRequest
    ): Response<ProfileInformationResponse>

}