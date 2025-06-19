package com.evirgenoguz.presentation.profile.editprofile

import androidx.lifecycle.viewModelScope
import com.evirgenoguz.core.domain.util.Result
import com.evirgenoguz.core.presentation.base.BaseViewModel
import com.evirgenoguz.domain.usecase.profile.UpdateUserModel
import com.evirgenoguz.domain.usecase.profile.UpdateUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.InputStream
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val updateUserUseCase: UpdateUserUseCase,
) : BaseViewModel() {

    private val _state = MutableStateFlow(EditProfileUiState())
    val state = _state.asStateFlow()

    private val eventChannel = Channel<EditProfileEvent>()
    val events = eventChannel.receiveAsFlow()

    fun onAction(action: EditProfileAction) {
        when (action) {
            is EditProfileAction.OnUpdateProfileClick -> {
                updateUser(action.updateUserModel)
            }

            is EditProfileAction.OnAvatarSelected -> saveAvatarState(action.inputStream)
        }
    }

    private fun saveAvatarState(inputStream: InputStream) {
        viewModelScope.launch {
            _state.update {
                _state.value.copy(
                    selectedAvatarInputStream = inputStream
                )
            }
        }
    }

    private fun updateUser(updateUserModel: UpdateUserModel) {
        viewModelScope.launch {
            showLoading()
            var updatedUserModelWithAvatarUrl = updateUserModel
            state.value.selectedAvatarInputStream?.let {
                updatedUserModelWithAvatarUrl = updateUserModel.copy(
                   inputStream = it,
                   imageFilename = "${updateUserModel.userName}/${UUID.randomUUID()}"
               )
            }

            val result = updateUserUseCase.invoke(updatedUserModelWithAvatarUrl)

            when (result) {
                is Result.Error -> {
                    eventChannel.send(EditProfileEvent.UpdateProfileError(result.error.message))
                }

                is Result.Success -> {
                    eventChannel.send(EditProfileEvent.UpdateProfileSuccess(result.data))
                }
            }
            hideLoading()
        }
    }
}