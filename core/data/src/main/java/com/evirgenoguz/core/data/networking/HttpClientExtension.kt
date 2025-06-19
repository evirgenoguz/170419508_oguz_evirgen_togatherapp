package com.evirgenoguz.core.data.networking

import com.evirgenoguz.core.domain.util.DataError
import com.evirgenoguz.core.domain.util.NetworkError
import com.evirgenoguz.core.domain.util.Result
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.serialization.SerializationException
import retrofit2.Response
import java.nio.channels.UnresolvedAddressException
import kotlin.coroutines.cancellation.CancellationException

inline fun <reified T> safeCall(execute: () -> Response<T>): Result<T> {
    val response = try {
        execute()
    } catch (e: UnresolvedAddressException) {
        e.printStackTrace()
        return Result.Error(
            NetworkError().copy(
                message = DataError.Network.NO_INTERNET.errorMessage,
                type = DataError.Network.NO_INTERNET
            )
        )
    } catch (_: SerializationException) {
        return Result.Error(
            NetworkError().copy(
                message = DataError.Network.SERIALIZATION.errorMessage,
                type = DataError.Network.SERIALIZATION
            )
        )
    } catch (e: Exception) {
        if (e is CancellationException) throw e
        e.printStackTrace()
        return Result.Error(NetworkError())
    }
    return responseToResult(response)
}


inline fun <reified T> responseToResult(response: Response<T>): Result<T> {
    return if (response.isSuccessful) {
        response.body()?.let { Result.Success(it) } ?: Result.Error(NetworkError())
    } else {
        var errorResponse = NetworkError()

        val errorBodyAsString = response.errorBody()?.string()
        if (errorBodyAsString.isNullOrEmpty().not()) {
            errorResponse = mapHttpError(response.code(), errorBodyAsString)
        }
        Result.Error(errorResponse)
    }
}

fun mapHttpError(code: Int, errorBody: String?): NetworkError {
    val gson = Gson()
    val type = object : TypeToken<ErrorResponseModel>() {}
    val errorResponseModel = gson.fromJson(errorBody, type)
    val domainError = errorResponseModel.toDomainError()
    return when (code) {
        401 -> domainError.copy(type = DataError.Network.UNAUTHORIZED)
        408 -> domainError.copy(type = DataError.Network.REQUEST_TIMEOUT)
        409 -> domainError.copy(type = DataError.Network.CONFLICT)
        413 -> domainError.copy(type = DataError.Network.PAYLOAD_TOO_LARGE)
        429 -> domainError.copy(type = DataError.Network.TOO_MANY_REQUESTS)
        in 500..599 -> domainError.copy(type = DataError.Network.SERVER_ERROR)
        else -> domainError
    }
}