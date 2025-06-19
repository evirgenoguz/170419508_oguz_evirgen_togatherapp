package com.evirgenoguz.core.presentation.di

import android.content.Context
import com.evirgenoguz.core.domain.util.util.IndicatorPresenter
import com.evirgenoguz.core.presentation.IndicatorPresenterImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
object ActivityModule {

    @Provides
    @ActivityScoped
    fun provideIndicatorPresenter(@ActivityContext context: Context): IndicatorPresenter =
        IndicatorPresenterImpl(context)

}