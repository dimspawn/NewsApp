package com.imaginatic.newsapp.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.switchMap
import com.newsapp.core.domain.model.NewsData
import com.newsapp.core.domain.usecase.NewsUseCase
import javax.inject.Inject

class MainViewModel @Inject constructor(private val newsUseCase: NewsUseCase): ViewModel() {
    private val news = MutableLiveData<NewsData>()
    val mainNews = newsUseCase.getMainNews().asLiveData()
    val readNews = news.switchMap { newsUseCase.setReadNews(it).asLiveData() }

    fun setReadNews(news: NewsData) {
        this.news.value = news
    }
}