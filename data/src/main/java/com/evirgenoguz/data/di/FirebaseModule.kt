package com.evirgenoguz.data.di

import com.evirgenoguz.data.datasource.firebase.FirebaseImageDataSource
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {

    @Provides
    @Singleton
    fun provideFirebaseStorage(): FirebaseStorage =
        FirebaseStorage.getInstance()

    @Provides
    @Singleton
    fun provideFirebaseImageDataSource(
        firebaseStorage: FirebaseStorage
    ): FirebaseImageDataSource = FirebaseImageDataSource(firebaseStorage)
}
