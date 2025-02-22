package com.newsapp.core.data.source.remote.network

import com.newsapp.core.data.source.remote.response.NewsMainResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("v2/top-headlines")
    suspend fun getMainNews(@Query("apiKey") apiKey: String, @Query("country") country: String = "us"): NewsMainResponse

    @GET("v2/top-headlines")
    suspend fun getSearchNews(@Query("apiKey") apiKey: String, @Query("q") query: String): NewsMainResponse
}