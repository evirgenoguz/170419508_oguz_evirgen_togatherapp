package com.evirgenoguz.data.repository

import com.evirgenoguz.core.domain.util.Result
import com.evirgenoguz.data.datasource.firebase.FirebaseImageDataSource
import com.evirgenoguz.domain.repository.ImageRepository
import java.io.InputStream
import javax.inject.Inject

class ImageRepositoryImpl @Inject constructor(
    private val firebaseImageDataSource: FirebaseImageDataSource
) : ImageRepository {

    override suspend fun uploadAvatarImage(
        inputStream: InputStream,
        filename: String
    ): Result<String> {
        return firebaseImageDataSource.uploadImageToFirebase(inputStream, filename)
    }
}