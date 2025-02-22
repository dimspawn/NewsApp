package com.newsapp.core.ui

import com.newsapp.core.domain.model.NewsData

interface NewsAdapterClickListener {
    fun onClickNews(news: NewsData)
}