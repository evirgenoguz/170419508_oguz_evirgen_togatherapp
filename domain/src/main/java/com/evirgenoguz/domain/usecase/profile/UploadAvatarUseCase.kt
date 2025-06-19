package com.evirgenoguz.domain.usecase.profile

import com.evirgenoguz.core.domain.util.Result
import com.evirgenoguz.domain.repository.ImageRepository
import java.io.InputStream
import javax.inject.Inject

class UploadAvatarUseCase @Inject constructor(
    private val imageRepository: ImageRepository
) {
    suspend operator fun invoke(inputStream: InputStream, fileName: String): Result<String> {
        return imageRepository.uploadAvatarImage(inputStream, fileName)
    }
}