package com.evirgenoguz.data.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.evirgenoguz.data.dao.UserDao
import com.evirgenoguz.data.datasource.local.AppDatabase
import com.evirgenoguz.data.datasource.local.ProfileLocalDataSource
import com.evirgenoguz.domain.util.ClearableCache
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalStorageModule {
    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("togather_token_shared_pref", Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "app.db")
            .fallbackToDestructiveMigration(false)
            .build()
    }

    @Provides
    fun provideUserDao(db: AppDatabase): UserDao = db.userDao()

    @Provides
    fun provideProfileLocalDataSource(userDao: UserDao): ProfileLocalDataSource = ProfileLocalDataSource(userDao)

    @Provides
    @IntoSet
    fun bindUserClearableCache(userCache: ProfileLocalDataSource): ClearableCache = userCache
}