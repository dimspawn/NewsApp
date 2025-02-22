package com.imaginatic.newsapp.read

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.newsapp.core.domain.usecase.NewsUseCase
import javax.inject.Inject

class ReadViewModel @Inject constructor(private val newsUseCase: NewsUseCase): ViewModel() {
    val readNews = newsUseCase.getReadNews().asLiveData()
}