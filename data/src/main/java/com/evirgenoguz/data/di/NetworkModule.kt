package com.evirgenoguz.data.di

import android.util.Log
import com.evirgenoguz.data.BuildConfig
import com.evirgenoguz.data.datasource.SampleRemoteDataSource
import com.evirgenoguz.data.datasource.remote.AuthRemoteDataSource
import com.evirgenoguz.data.datasource.remote.CampaignRemoteDataSource
import com.evirgenoguz.data.datasource.remote.EventRemoteDataSource
import com.evirgenoguz.data.datasource.remote.GroupRemoteDataSource
import com.evirgenoguz.data.datasource.remote.HomeDataSource
import com.evirgenoguz.data.datasource.remote.ProfileDataSource
import com.evirgenoguz.data.network.AuthApi
import com.evirgenoguz.data.network.CampaignApi
import com.evirgenoguz.data.network.EventApi
import com.evirgenoguz.data.network.GroupApi
import com.evirgenoguz.data.network.HomeApi
import com.evirgenoguz.data.network.ProfileApi
import com.evirgenoguz.data.network.RefreshTokenApi
import com.evirgenoguz.data.network.SampleApi
import com.evirgenoguz.data.util.AuthInterceptor
import com.evirgenoguz.data.util.RefreshAuthenticator
import com.evirgenoguz.data.util.TokenAuthenticator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideOkHttpClient(
        authInterceptor: AuthInterceptor,
        tokenAuthenticator: TokenAuthenticator
    ): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor { message ->
            Log.d("OkHttp", message)
        }.apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(authInterceptor)
            .authenticator(tokenAuthenticator)
            .build()
    }

    @Provides
    @Singleton
    fun provideSampleApi(retrofit: Retrofit): SampleApi = retrofit.create(SampleApi::class.java)

    @Provides
    @Singleton
    fun provideAuthApi(retrofit: Retrofit): AuthApi = retrofit.create(AuthApi::class.java)

    @Provides
    @Singleton
    fun provideSampleDatasource(sampleApi: SampleApi): SampleRemoteDataSource =
        SampleRemoteDataSource(sampleApi)

    @Provides
    @Singleton
    fun provideAuthDatasource(authApi: AuthApi): AuthRemoteDataSource =
        AuthRemoteDataSource(authApi)


    @Provides
    @Singleton
    fun provideProfileApi(retrofit: Retrofit): ProfileApi = retrofit.create(ProfileApi::class.java)

    @Provides
    @Singleton
    fun provideProfileDataSource(profileApi: ProfileApi): ProfileDataSource =
        ProfileDataSource(profileApi)

    @Provides
    @Singleton
    fun provideHomeApi(retrofit: Retrofit): HomeApi = retrofit.create(HomeApi::class.java)

    @Provides
    @Singleton
    fun provideHomeDataSource(homeApi: HomeApi): HomeDataSource =
        HomeDataSource(homeApi)

    @Provides
    @Singleton
    fun provideGroupApi(retrofit: Retrofit): GroupApi = retrofit.create(GroupApi::class.java)

    @Provides
    @Singleton
    fun provideGroupDataSource(groupApi: GroupApi): GroupRemoteDataSource =
        GroupRemoteDataSource(groupApi)

    @Provides
    @Singleton
    fun provideEventApi(retrofit: Retrofit): EventApi = retrofit.create(EventApi::class.java)

    @Provides
    @Singleton
    fun provideEventDataSource(eventApi: EventApi): EventRemoteDataSource =
        EventRemoteDataSource(eventApi)

    @Provides
    @Singleton
    fun provideCampaignApi(retrofit: Retrofit): CampaignApi = retrofit.create(CampaignApi::class.java)

    @Provides
    @Singleton
    fun provideCampaignDataSource(campaignApi: CampaignApi): CampaignRemoteDataSource =
        CampaignRemoteDataSource(campaignApi)

    @Provides
    @Singleton
    @Named("refreshOkHttpClient")
    fun provideRefreshOkHttpClient(
        refreshAuthenticator: RefreshAuthenticator
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor { Log.d("RefreshOkHttp", it) }
                    .apply { level = HttpLoggingInterceptor.Level.BODY }
            )
            .authenticator(refreshAuthenticator)
            .build()
    }

    @Provides
    @Singleton
    @Named("refreshRetrofit")
    fun provideRefreshRetrofit(
        @Named("refreshOkHttpClient") okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideRefreshAuthApi(@Named("refreshRetrofit") retrofit: Retrofit): RefreshTokenApi =
        retrofit.create(
            RefreshTokenApi::class.java
        )
}