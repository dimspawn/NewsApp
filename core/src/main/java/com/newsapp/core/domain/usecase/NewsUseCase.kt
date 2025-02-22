package com.newsapp.core.domain.usecase

import com.newsapp.core.data.Resource
import com.newsapp.core.domain.model.NewsData
import kotlinx.coroutines.flow.Flow

interface NewsUseCase {
    fun getMainNews(): Flow<Resource<List<NewsData>>>
    fun setReadNews(news: NewsData): Flow<Resource<String>>
    fun getReadNews(): Flow<Resource<List<NewsData>>>
    fun setSearchNews(query: String): Flow<Resource<List<NewsData>>>
}