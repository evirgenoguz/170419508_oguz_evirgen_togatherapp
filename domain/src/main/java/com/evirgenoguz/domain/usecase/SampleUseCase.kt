package com.evirgenoguz.domain.usecase

import com.evirgenoguz.core.domain.util.Result
import com.evirgenoguz.domain.model.SampleModel
import com.evirgenoguz.domain.repository.SampleRepository
import javax.inject.Inject

class SampleUseCase @Inject constructor(
    private val sampleRepository: SampleRepository
) {
    suspend operator fun invoke(): Result<SampleModel> {
        return sampleRepository.getSampleData()
    }
}