package com.evirgenoguz.presentation.splash

sealed interface SplashAction {
    data object ReadyToNavigate: SplashAction
}