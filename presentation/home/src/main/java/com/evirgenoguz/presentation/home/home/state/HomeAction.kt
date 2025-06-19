package com.evirgenoguz.presentation.home.home.state

sealed interface HomeAction {
    data object OnGroupButtonClick: HomeAction
    data object OnEventButtonClick: HomeAction
}