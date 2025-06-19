package com.evirgenoguz.data.datasource.remote

import com.evirgenoguz.core.data.networking.safeCall
import com.evirgenoguz.data.network.ProfileApi
import com.evirgenoguz.core.domain.util.Result
import com.evirgenoguz.data.model.request.profile.toRequestModel
import com.evirgenoguz.data.model.response.profile.toDomainModel
import com.evirgenoguz.domain.model.profile.ChangeLocationModel
import com.evirgenoguz.domain.model.profile.ChangePasswordModel
import com.evirgenoguz.domain.model.profile.CityLookup
import com.evirgenoguz.domain.model.profile.CountryLookup
import com.evirgenoguz.domain.model.profile.DistrictLookup
import com.evirgenoguz.domain.model.profile.ProfileUserModel
import com.evirgenoguz.domain.usecase.profile.UpdateUserModel

class ProfileDataSource(
    private val profileApi: ProfileApi
) {
    suspend fun getProfileInformation(): Result<ProfileUserModel> {
        val result = safeCall {
            profileApi.getProfile()
        }

        return when (result) {
            is Result.Error -> {
                Result.Error(result.error)
            }

            is Result.Success -> {
                Result.Success(result.data.toDomainModel())
            }
        }
    }

    suspend fun updateUserProfile(updateUserModel: UpdateUserModel): Result<ProfileUserModel> {
        val result = safeCall {
            val updateUserProfileRequest = updateUserModel.toRequestModel()
            profileApi.updateUserProfile(updateUserProfileRequest)
        }

        return when (result) {
            is Result.Error -> {
                Result.Error(result.error)
            }

            is Result.Success -> {
                Result.Success(result.data.toDomainModel())
            }
        }
    }

    suspend fun changePassword(changePasswordModel: ChangePasswordModel): Result<Unit> {
        val result = safeCall {
            profileApi.changePassword(changePasswordModel.toRequestModel())
        }

        return when (result) {
            is Result.Error -> Result.Error(result.error)
            is Result.Success -> Result.Success(Unit)
        }
    }

    suspend fun lookupCountries(): Result<List<CountryLookup>> {
        val result = safeCall {
            profileApi.lookupCountries()
        }
        return when (result) {
            is Result.Error -> {
                Result.Error(result.error)
            }

            is Result.Success -> {
                Result.Success(result.data.toDomainModel())
            }
        }
    }

    suspend fun lookupCities(code: String): Result<List<CityLookup>> {
        val result = safeCall {
            profileApi.lookupCities(code)
        }
        return when (result) {
            is Result.Error -> {
                Result.Error(result.error)
            }

            is Result.Success -> {
                Result.Success(result.data.toDomainModel())
            }
        }
    }

    suspend fun lookupDistricts(cityCode: String): Result<List<DistrictLookup>> {
        val result = safeCall {
            profileApi.lookupDistricts(cityCode)
        }
        return when (result) {
            is Result.Error -> {
                Result.Error(result.error)
            }

            is Result.Success -> {
                Result.Success(result.data.toDomainModel())
            }
        }
    }

    suspend fun updateLocation(changeLocationModel: ChangeLocationModel): Result<ProfileUserModel> {
        val result = safeCall {
            profileApi.updateLocation(changeLocationModel.toRequestModel())
        }

        return when (result) {
            is Result.Error -> Result.Error(result.error)
            is Result.Success -> Result.Success(result.data.toDomainModel())
        }
    }
}