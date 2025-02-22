package com.imaginatic.newsapp.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.switchMap
import com.newsapp.core.domain.usecase.NewsUseCase
import javax.inject.Inject

class SearchViewModel @Inject constructor(private val newsUseCase: NewsUseCase): ViewModel() {
    private val query = MutableLiveData<String>()

    val searchNews = query.switchMap { newsUseCase.setSearchNews(it).asLiveData() }

    fun setQuery(query: String) {
        this.query.value = query
    }
}