package com.evirgenoguz.domain.repository

import com.evirgenoguz.core.domain.util.Result
import com.evirgenoguz.domain.model.profile.ChangeLocationModel
import com.evirgenoguz.domain.model.profile.ChangePasswordModel
import com.evirgenoguz.domain.model.profile.CityLookup
import com.evirgenoguz.domain.model.profile.CountryLookup
import com.evirgenoguz.domain.model.profile.DistrictLookup
import com.evirgenoguz.domain.model.profile.ProfileUserModel
import com.evirgenoguz.domain.usecase.profile.UpdateUserModel

interface ProfileRepository {
    suspend fun getProfileInformation(): Result<ProfileUserModel>
    suspend fun updateUserProfile(updateUserModel: UpdateUserModel): Result<ProfileUserModel>
    suspend fun changePassword(changePasswordModel: ChangePasswordModel): Result<Unit>
    suspend fun lookupCountries(): Result<List<CountryLookup>>
    suspend fun lookupCities(code: String): Result<List<CityLookup>>
    suspend fun lookupDistricts(cityCode: String): Result<List<DistrictLookup>>
    suspend fun updateLocation(changeLocationModel: ChangeLocationModel): Result<ProfileUserModel>
}