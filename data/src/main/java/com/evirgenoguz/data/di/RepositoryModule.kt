package com.evirgenoguz.data.di

import com.evirgenoguz.data.repository.AuthRepositoryImpl
import com.evirgenoguz.data.repository.CampaignRepositoryImpl
import com.evirgenoguz.data.repository.EventRepositoryImpl
import com.evirgenoguz.data.repository.GroupRepositoryImpl
import com.evirgenoguz.data.repository.HomeRepositoryImpl
import com.evirgenoguz.data.repository.ImageRepositoryImpl
import com.evirgenoguz.data.repository.ProfileRepositoryImpl
import com.evirgenoguz.data.repository.SampleRepositoryImpl
import com.evirgenoguz.domain.repository.AuthRepository
import com.evirgenoguz.domain.repository.CampaignRepository
import com.evirgenoguz.domain.repository.EventRepository
import com.evirgenoguz.domain.repository.GroupRepository
import com.evirgenoguz.domain.repository.HomeRepository
import com.evirgenoguz.domain.repository.ImageRepository
import com.evirgenoguz.domain.repository.ProfileRepository
import com.evirgenoguz.domain.repository.SampleRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindSampleRepository(sampleRepositoryImpl: SampleRepositoryImpl): SampleRepository

    @Binds
    abstract fun bindAuthRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository

    @Binds
    @Singleton
    abstract fun bindProfileRepository(impl: ProfileRepositoryImpl): ProfileRepository

    @Binds
    abstract fun bindHomeRepository(homeRepositoryImpl: HomeRepositoryImpl): HomeRepository

    @Binds
    @Singleton
    abstract fun bindImageRepository(imageRepositoryImpl: ImageRepositoryImpl): ImageRepository

    @Binds
    @Singleton
    abstract fun bindGroupRepository(groupRepositoryImpl: GroupRepositoryImpl): GroupRepository

    @Binds
    @Singleton
    abstract fun bindEventRepository(eventRepositoryImpl: EventRepositoryImpl): EventRepository

    @Binds
    @Singleton
    abstract fun bindCampaignRepository(campaignRepositoryImpl: CampaignRepositoryImpl): CampaignRepository
}