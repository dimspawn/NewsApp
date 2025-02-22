package com.newsapp.core.di

import com.newsapp.core.data.source.NewsRepository
import com.newsapp.core.domain.repository.INewsRepository
import com.newsapp.core.domain.usecase.NewsInteract
import com.newsapp.core.domain.usecase.NewsUseCase
import dagger.Binds
import dagger.Module

@Module(includes = [NetworkModule::class, DatabaseModule::class])
abstract class RepositoryModule {
    @Binds
    abstract fun provideRepository(newsRepository: NewsRepository): INewsRepository

    @Binds
    abstract fun provideNewsUseCase(newsInteract: NewsInteract): NewsUseCase
}