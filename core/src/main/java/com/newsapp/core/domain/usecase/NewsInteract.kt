package com.newsapp.core.domain.usecase

import com.newsapp.core.data.Resource
import com.newsapp.core.data.source.NewsRepository
import com.newsapp.core.domain.model.NewsData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NewsInteract @Inject constructor(private val newsRepository: NewsRepository): NewsUseCase{
    override fun getMainNews() = newsRepository.getMainNews()
    override fun setReadNews(news: NewsData) = newsRepository.setReadNews(news)
    override fun getReadNews() = newsRepository.getReadNews()
    override fun setSearchNews(query: String) = newsRepository.setSearchNews(query)
}