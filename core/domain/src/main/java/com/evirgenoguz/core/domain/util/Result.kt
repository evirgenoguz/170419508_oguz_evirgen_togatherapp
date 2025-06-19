package com.evirgenoguz.core.domain.util

sealed interface Result<out D> {
    data class Success<out D>(val data: D) : Result<D>
    data class Error(val error: NetworkError) : Result<Nothing>
}
