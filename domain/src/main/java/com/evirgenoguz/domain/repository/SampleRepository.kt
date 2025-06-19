package com.evirgenoguz.domain.repository

import com.evirgenoguz.core.domain.util.Result
import com.evirgenoguz.domain.model.SampleModel

interface SampleRepository {

    suspend fun getSampleData(): Result<SampleModel>
}