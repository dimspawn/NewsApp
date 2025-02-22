package com.newsapp.core.domain.repository

import com.newsapp.core.data.Resource
import com.newsapp.core.domain.model.NewsData
import kotlinx.coroutines.flow.Flow

interface INewsRepository {
    fun getMainNews(): Flow<Resource<List<NewsData>>>
    fun setReadNews(news: NewsData): Flow<Resource<String>>
    fun getReadNews(): Flow<Resource<List<NewsData>>>
    fun setSearchNews(query: String): Flow<Resource<List<NewsData>>>
}