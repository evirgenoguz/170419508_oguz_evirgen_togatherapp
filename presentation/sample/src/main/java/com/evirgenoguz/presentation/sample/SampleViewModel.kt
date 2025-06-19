package com.evirgenoguz.presentation.sample

import androidx.lifecycle.viewModelScope
import com.evirgenoguz.core.domain.util.Result
import com.evirgenoguz.core.presentation.asString
import com.evirgenoguz.core.presentation.base.BaseViewModel
import com.evirgenoguz.domain.usecase.SampleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SampleViewModel @Inject constructor(
    private val sampleUseCase: SampleUseCase
) : BaseViewModel() {

    private val _state = MutableStateFlow(SampleState(""))
    val state = _state.asStateFlow()

    private val eventChannel = Channel<SampleEvent>()
    val events = eventChannel.receiveAsFlow()
    fun getSampleData() {
        viewModelScope.launch {
            showLoading()
            delay(2000)
            when (val result = sampleUseCase()) {
                is Result.Error -> {
                    eventChannel.send(SampleEvent.Error(result.error.asString()))
                    //if you want to pop errorDialog use
                    //showErrorDialog(result.error.asString())
                }

                is Result.Success -> {
                    _state.update {
                        it.copy(data = result.data.sampleData)
                    }
                    eventChannel.send(SampleEvent.DataSuccess)
                }
            }
            hideLoading()
        }
    }
}