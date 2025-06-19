package com.evirgenoguz.data.repository

import com.evirgenoguz.core.domain.util.Result
import com.evirgenoguz.data.datasource.local.ProfileLocalDataSource
import com.evirgenoguz.data.datasource.remote.ProfileDataSource
import com.evirgenoguz.data.model.entity.toEntity
import com.evirgenoguz.domain.model.profile.ChangeLocationModel
import com.evirgenoguz.domain.model.profile.ChangePasswordModel
import com.evirgenoguz.domain.model.profile.CityLookup
import com.evirgenoguz.domain.model.profile.CountryLookup
import com.evirgenoguz.domain.model.profile.DistrictLookup
import com.evirgenoguz.domain.model.profile.ProfileUserModel
import com.evirgenoguz.domain.repository.ProfileRepository
import com.evirgenoguz.domain.usecase.profile.UpdateUserModel
import com.evirgenoguz.domain.util.SessionManager
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val profileDataSource: ProfileDataSource,
    private val profileLocalDataSource: ProfileLocalDataSource
) : ProfileRepository {
    override suspend fun getProfileInformation(): Result<ProfileUserModel> {
        val localProfile = profileLocalDataSource.getUserById(SessionManager.getCurrentUser()?.username.toString())
        if (localProfile != null) {
            return Result.Success(localProfile)
        }
        return profileDataSource.getProfileInformation().also {
            if (it is Result.Success) {
                profileLocalDataSource.saveUser(it.data.toEntity())
            }
        }
    }

    override suspend fun updateUserProfile(updateUserModel: UpdateUserModel): Result<ProfileUserModel> {
        return profileDataSource.updateUserProfile(updateUserModel).also {
            if (it is Result.Success) {
                profileLocalDataSource.saveUser(it.data.toEntity())
            }
        }
    }

    override suspend fun changePassword(changePasswordModel: ChangePasswordModel): Result<Unit> {
        return profileDataSource.changePassword(changePasswordModel)
    }

    override suspend fun lookupCountries(): Result<List<CountryLookup>> {
        return profileDataSource.lookupCountries()
    }

    override suspend fun lookupCities(code: String): Result<List<CityLookup>> {
        return profileDataSource.lookupCities(code)
    }

    override suspend fun lookupDistricts(cityCode: String): Result<List<DistrictLookup>> {
        return profileDataSource.lookupDistricts(cityCode)
    }

    override suspend fun updateLocation(changeLocationModel: ChangeLocationModel): Result<ProfileUserModel> {
        return profileDataSource.updateLocation(changeLocationModel)
    }
}