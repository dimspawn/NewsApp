package com.newsapp.core.domain.model

data class RefreshData(
    val id: Int,
    val entityName: String,
    val duration: Long,
    val period: Int
)