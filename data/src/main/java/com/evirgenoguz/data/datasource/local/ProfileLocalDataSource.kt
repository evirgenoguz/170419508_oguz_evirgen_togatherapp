package com.evirgenoguz.data.datasource.local

import com.evirgenoguz.data.dao.UserDao
import com.evirgenoguz.data.model.entity.UserEntity
import com.evirgenoguz.data.model.entity.toDomainModel
import com.evirgenoguz.domain.model.profile.ProfileUserModel
import com.evirgenoguz.domain.util.ClearableCache
import javax.inject.Inject

class ProfileLocalDataSource @Inject constructor(
    private val userDao: UserDao
) : ClearableCache {
    private val timeToLive = 60 * 60 * 1000L

    suspend fun saveUser(user: UserEntity) {
        userDao.insertUser(user)
    }

    suspend fun getUserById(username: String): ProfileUserModel? {
        return userDao.getUserByUserName(username)?.toDomainModel()
        val entity = userDao.getUserByUserName(username) ?: return null
        val now = System.currentTimeMillis()
        return if ((now - entity.cachedAt) <= timeToLive) {
            entity.toDomainModel()
        } else {
            null
        }
    }

    override suspend fun clearExpiredCache() {
        val threshold = System.currentTimeMillis() - timeToLive
        userDao.deleteExpired(threshold)
    }

    override suspend fun clearAllCache() {
        userDao.clearAll()
    }
}