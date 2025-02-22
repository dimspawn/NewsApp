package com.newsapp.core.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "news")
data class NewsEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: String,
    @ColumnInfo(name = "author")
    var author: String,
    @ColumnInfo(name = "title")
    var title: String,
    @ColumnInfo(name = "description")
    var description: String,
    @ColumnInfo(name = "url")
    var url: String,
    @ColumnInfo(name = "urlToString")
    var urlToString: String?,
    @ColumnInfo(name = "sourceName")
    var sourceName: String,
    @ColumnInfo(name = "publishedAt")
    var publishedAt: String,
    @ColumnInfo(name = "content")
    var content: String,
    @ColumnInfo(name = "isRead")
    var isRead: Boolean,
    @ColumnInfo(name = "isSearch")
    var isSearch: Boolean
)