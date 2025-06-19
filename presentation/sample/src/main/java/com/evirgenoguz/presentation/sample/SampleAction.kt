package com.evirgenoguz.presentation.sample

sealed interface SampleAction {
    data object OnNavigateNextSampleClick: SampleAction
}