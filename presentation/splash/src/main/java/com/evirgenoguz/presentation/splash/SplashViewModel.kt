package com.evirgenoguz.presentation.splash

import androidx.lifecycle.viewModelScope
import com.evirgenoguz.core.presentation.base.BaseViewModel
import com.evirgenoguz.domain.usecase.auth.GetAccessTokenUseCase
import com.evirgenoguz.domain.util.ClearableCache
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getAccessTokenUseCase: GetAccessTokenUseCase,
    private val clearableCaches: Set<@JvmSuppressWildcards ClearableCache>
) : BaseViewModel() {

    private val _state = MutableStateFlow(SplashState())
    val state = _state.asStateFlow()

    fun getAccessToken() {
        viewModelScope.launch {
            _state.value.accessToken = getAccessTokenUseCase.invoke()
        }
    }

    fun clearAllCache() {
        viewModelScope.launch {
            clearableCaches.forEach { it.clearAllCache() }
        }
    }
}