package com.newsapp.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class SourceResponse(
    @field:SerializedName("id")
    var id: String? = null,
    @field:SerializedName("name")
    var name: String? = null
)