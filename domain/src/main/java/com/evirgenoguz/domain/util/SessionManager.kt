package com.evirgenoguz.domain.util

import com.evirgenoguz.domain.model.profile.ProfileUserModel
import kotlinx.coroutines.flow.MutableStateFlow

object SessionManager {
    @Volatile
    private var currentUser: ProfileUserModel? = null

    val isNextEventLessThanTwoHoursAway = MutableStateFlow<Boolean>(false)

    fun updateUser(user: ProfileUserModel) {
        currentUser = user
    }

    fun getCurrentUser(): ProfileUserModel? {
        return currentUser
    }

    fun setIsNextEventLessThanTwoHoursAway(bool: Boolean) {
        isNextEventLessThanTwoHoursAway.value = bool
    }
}