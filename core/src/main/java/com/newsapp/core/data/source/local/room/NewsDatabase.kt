package com.newsapp.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.newsapp.core.data.source.local.entity.NewsEntity
import com.newsapp.core.data.source.local.entity.RefreshEntity

@Database(entities = [NewsEntity::class, RefreshEntity::class], version = 1, exportSchema = false)
abstract class NewsDatabase: RoomDatabase() {
    abstract fun newsDao(): NewsDao
}