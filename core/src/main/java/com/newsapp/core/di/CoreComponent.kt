package com.newsapp.core.di

import android.content.Context
import com.newsapp.core.domain.repository.INewsRepository
import com.newsapp.core.domain.usecase.NewsUseCase
import dagger.BindsInstance
import dagger.Component

@CoreScope
@Component(modules = [RepositoryModule::class])
interface CoreComponent {
    @Component.Builder
    interface Builder {
        fun context(@BindsInstance context: Context): Builder
        fun build(): CoreComponent
    }

    fun provideNewsUseCase(): NewsUseCase
    fun provideRepository(): INewsRepository
}