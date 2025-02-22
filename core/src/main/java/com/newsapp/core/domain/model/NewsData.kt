package com.newsapp.core.domain.model

data class NewsData(
    val id: String,
    val author: String,
    val title: String,
    val description: String,
    val url: String,
    val urlToString: String?,
    val sourceName: String,
    val publishedAt: String,
    val content: String,
    val isRead: Boolean,
    val isSearch: Boolean
)