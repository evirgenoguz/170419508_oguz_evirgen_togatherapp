package com.evirgenoguz.core.presentation.util

import androidx.lifecycle.LiveData

interface SystemBarInsets {

    val navigationBarHeightLiveData: LiveData<Int>

    val statusBarHeightLiveData: LiveData<Int>

    var isBottomNavigationVisible: Boolean

    fun setTopPaddingToAppBar(height: Int)

    fun setBottomPaddingToBottomNavigation(height: Int)

}