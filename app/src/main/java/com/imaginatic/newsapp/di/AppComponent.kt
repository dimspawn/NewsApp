package com.imaginatic.newsapp.di

import com.imaginatic.newsapp.main.MainActivity
import com.imaginatic.newsapp.read.ReadActivity
import com.imaginatic.newsapp.search.SearchActivity
import com.newsapp.core.di.CoreComponent
import com.newsapp.core.domain.usecase.NewsUseCase
import dagger.Component

@AppScope
@Component(
    modules = [ViewModelModule::class], dependencies = [CoreComponent::class]
)
interface AppComponent {
    @Component.Builder
    interface Builder {
        fun coreComponent(coreComponent: CoreComponent): Builder
        fun build(): AppComponent
    }

    fun provideNewsUseCase(): NewsUseCase

    fun inject(activity: MainActivity)
    fun inject(activity: ReadActivity)
    fun inject(activity: SearchActivity)
}