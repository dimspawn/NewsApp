package com.newsapp.core.data.source.local

import com.newsapp.core.data.source.local.entity.NewsEntity
import com.newsapp.core.data.source.local.room.NewsDao
import com.newsapp.core.di.CoreScope
import javax.inject.Inject

@CoreScope
class LocalDataSource @Inject constructor(private val newsDao: NewsDao) {
    suspend fun insertNews(news: NewsEntity) { newsDao.insertNews(news) }
    suspend fun getNewsById(id: String) = newsDao.getNewsById(id)
    fun getMainNews(isSearch: Boolean) = newsDao.getMainNews(isSearch)
    fun getReadNewsNoFlow() = newsDao.getReadNewsNoFlow(true)
    fun getRefreshByEntityName(entityName: String) = newsDao.getRefreshByEntityName(entityName)
    suspend fun updateNews(news: NewsEntity) { newsDao.updateNews(news) }
    suspend fun updateReadNews(id: String, isRead: Boolean) { newsDao.updateReadNews(id, isRead) }
    suspend fun modifyAllSearchNews(isSearch: Boolean) { newsDao.modifyAllSearchNews(isSearch) }
    suspend fun deleteAllNews(isRead: Boolean) { newsDao.deleteAllNews(isRead) }
}