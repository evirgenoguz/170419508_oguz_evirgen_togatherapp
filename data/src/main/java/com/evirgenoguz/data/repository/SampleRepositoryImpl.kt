package com.evirgenoguz.data.repository

import com.evirgenoguz.core.domain.util.Result
import com.evirgenoguz.data.datasource.SampleRemoteDataSource
import com.evirgenoguz.domain.model.SampleModel
import com.evirgenoguz.domain.repository.SampleRepository
import javax.inject.Inject

class SampleRepositoryImpl @Inject constructor(
    private val sampleRemoteDataSource: SampleRemoteDataSource
) : SampleRepository {
    override suspend fun getSampleData(): Result<SampleModel> {
        return sampleRemoteDataSource.getSampleData()
    }

}