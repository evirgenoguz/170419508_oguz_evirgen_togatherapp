package com.evirgenoguz.data.di

import android.Manifest
import android.content.Context
import androidx.annotation.RequiresPermission
import com.evirgenoguz.data.logger.Analytics
import com.evirgenoguz.data.logger.ConsoleEventLogger
import com.evirgenoguz.data.logger.FirebaseEventLogger
import com.evirgenoguz.domain.util.EventLogger
import com.google.firebase.analytics.FirebaseAnalytics
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AnalyticsModule {
    @RequiresPermission(allOf = [Manifest.permission.INTERNET, Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.WAKE_LOCK])
    @Provides
    @IntoSet
    fun provideFirebaseLogger(
        @ApplicationContext context: Context
    ): EventLogger {
        return FirebaseEventLogger(FirebaseAnalytics.getInstance(context))
    }

    @Provides
    @IntoSet
    fun provideConsoleLogger(): EventLogger {
        return ConsoleEventLogger()
    }

    @Provides
    @Singleton
    fun bindEventLogger(
        loggers: Set<@JvmSuppressWildcards EventLogger>
    ): EventLogger = Analytics(loggers)
}