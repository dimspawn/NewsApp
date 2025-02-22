package com.newsapp.core.di

import android.content.Context
import androidx.room.Room
import com.newsapp.core.data.source.local.room.NewsDao
import com.newsapp.core.data.source.local.room.NewsDatabase
import dagger.Module
import dagger.Provides

@Module
class DatabaseModule {
    @CoreScope
    @Provides
    fun provideDatabase(context: Context): NewsDatabase {
        return Room.databaseBuilder(
            context,
            NewsDatabase::class.java, "News.db"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    fun provideNewsDao(database: NewsDatabase): NewsDao = database.newsDao()
}