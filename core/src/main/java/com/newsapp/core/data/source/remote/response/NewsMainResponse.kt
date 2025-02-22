package com.newsapp.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class NewsMainResponse(
    @field:SerializedName("status")
    var status: String? = null,
    @field:SerializedName("totalResults")
    var totalResults: Int? = null,
    @field:SerializedName("articles")
    var articles: List<NewsResponse>? = null,
    @field:SerializedName("message")
    var message: String? = null
)