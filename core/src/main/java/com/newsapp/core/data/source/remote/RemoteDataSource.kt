package com.newsapp.core.data.source.remote

import android.util.Log
import com.newsapp.core.BuildConfig
import com.newsapp.core.data.source.remote.network.ApiResponse
import com.newsapp.core.data.source.remote.network.ApiService
import com.newsapp.core.data.source.remote.response.NewsResponse
import kotlinx.coroutines.flow.flowOn
import com.newsapp.core.di.CoreScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@CoreScope
class RemoteDataSource @Inject constructor(private val apiService: ApiService) {
    fun getMainNews(): Flow<ApiResponse<List<NewsResponse>>> = flow<ApiResponse<List<NewsResponse>>> {
        val reported = try {
            val news = mutableListOf<NewsResponse>()
            val response = apiService.getMainNews(BuildConfig.APIKEY)
            if (response.status == "ok") {
                response.articles?.let {
                    news.addAll(it)
                }
                ApiResponse.Success(news)
            } else {
                var message = "Error Happened"
                response.message?.let {
                    message = it
                }
                ApiResponse.Error(message)
            }
        } catch (e: Exception) {
            Log.e("RemoteDataSource", e.message.toString())
            ApiResponse.Error(e.message.toString())
        }
        emit(reported)
    }.flowOn(Dispatchers.IO)

    fun getSearchNews(query: String):Flow<ApiResponse<List<NewsResponse>>> = flow<ApiResponse<List<NewsResponse>>> {
        val searched = try {
            val news = mutableListOf<NewsResponse>()
            val response = apiService.getSearchNews(BuildConfig.APIKEY, query)
            if (response.status == "ok") {
                response.articles?.let {
                    news.addAll(it)
                }
            }
            ApiResponse.Success(news)
        } catch (e: Exception) {
            Log.e("RemoteDataSource", e.message.toString())
            ApiResponse.Error(e.message.toString())
        }
        emit(searched)
    }.flowOn(Dispatchers.IO)
}