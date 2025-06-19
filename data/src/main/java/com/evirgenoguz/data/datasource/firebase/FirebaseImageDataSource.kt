package com.evirgenoguz.data.datasource.firebase

import android.util.Log
import com.evirgenoguz.core.domain.util.NetworkError
import com.evirgenoguz.core.domain.util.Result
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.suspendCancellableCoroutine
import java.io.InputStream
import javax.inject.Inject

class FirebaseImageDataSource @Inject constructor(
    private val firebaseStorage: FirebaseStorage
) {
    suspend fun uploadImageToFirebase(
        inputStream: InputStream,
        fileName: String
    ): Result<String> = suspendCancellableCoroutine { cont ->

        val ref = firebaseStorage.reference.child("images/$fileName")
        val uploadTask = ref.putStream(inputStream)
        Log.d("Oguz Evirgen", "filename: $fileName, inputStream: $inputStream")

        uploadTask.addOnSuccessListener {
            ref.downloadUrl
                .addOnSuccessListener { uri ->
                    if (cont.isActive) {
                        cont.resumeWith(
                            kotlin.Result.success(Result.Success(uri.toString()))
                        )
                    }
                }
                .addOnFailureListener { error ->
                    if (cont.isActive) {
                        cont.resumeWith(
                            kotlin.Result.success(
                                Result.Error(
                                    NetworkError(message = error.message ?: "Failed to get download URL")
                                )
                            )
                        )
                    }
                }
        }.addOnFailureListener { error ->
            if (cont.isActive) {
                cont.resumeWith(
                    kotlin.Result.success(
                        Result.Error(
                            NetworkError(message = error.message.orEmpty())
                        )
                    )
                )
            }
        }
    }
}