package com.evirgenoguz.domain.repository

import com.evirgenoguz.core.domain.util.Result
import java.io.InputStream


interface ImageRepository {
    suspend fun uploadAvatarImage(inputStream: InputStream,  filename: String): Result<String>
}