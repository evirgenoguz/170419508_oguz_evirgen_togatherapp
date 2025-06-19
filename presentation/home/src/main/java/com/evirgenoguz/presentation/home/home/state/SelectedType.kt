package com.evirgenoguz.presentation.home.home.state

sealed interface SelectedType {
    data object Event : SelectedType
    data object Group : SelectedType
}