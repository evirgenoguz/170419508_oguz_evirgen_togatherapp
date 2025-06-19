package com.evirgenoguz.data.datasource

import com.evirgenoguz.core.data.networking.safeCall
import com.evirgenoguz.core.domain.util.Result
import com.evirgenoguz.data.network.SampleApi
import com.evirgenoguz.domain.model.SampleModel

class SampleRemoteDataSource(
    private val sampleApi: SampleApi
) {
    suspend fun getSampleData(): Result<SampleModel> {
        val result = safeCall {
            sampleApi.getSample()
        }
        return when(result) {
            is Result.Error -> {
                Result.Success(SampleModel("Test"))
                Result.Error(result.error)
            }
            is Result.Success -> {
                Result.Success(SampleModel(result.data.sampleData))
            }
        }
    }
}