package com.newsapp.core.utils

import com.newsapp.core.data.source.local.entity.NewsEntity
import com.newsapp.core.data.source.local.entity.RefreshEntity
import com.newsapp.core.data.source.remote.response.NewsResponse
import com.newsapp.core.domain.model.NewsData
import com.newsapp.core.domain.model.RefreshData

object DataMapper {
    private fun mapRefreshEntityDomain(refreshEntity: RefreshEntity): RefreshData = RefreshData(
        id = refreshEntity.id,
        entityName = refreshEntity.entityName,
        duration = refreshEntity.duration,
        period = refreshEntity.period
    )

    fun mapRefreshEntitiesToDomains(refreshEntities: List<RefreshEntity>): List<RefreshData> = refreshEntities.map {
        mapRefreshEntityDomain(it)
    }

    fun mapNewsEntityToDomain(newsEntity: NewsEntity): NewsData = NewsData(
        id = newsEntity.id,
        author = newsEntity.author,
        title = newsEntity.title,
        description = newsEntity.description,
        url = newsEntity.url,
        urlToString = newsEntity.urlToString,
        sourceName = newsEntity.sourceName,
        publishedAt = newsEntity.publishedAt,
        content = newsEntity.content,
        isRead = newsEntity.isRead,
        isSearch = newsEntity.isSearch
    )

    fun mapNewsEntitiesToDomains(newsEntities: List<NewsEntity>): List<NewsData> = newsEntities.map {
        mapNewsEntityToDomain(it)
    }

    private fun mapNewsResponseToEntity(newsResponse: NewsResponse, isRead: Boolean = false, isSearch: Boolean = false): NewsEntity = NewsEntity(
        id = Helper.md5orBlank(newsResponse.url),
        author = newsResponse.author ?: "[blank_author]",
        title = newsResponse.title ?: "[blank_title]",
        description = newsResponse.description ?: "-",
        url = newsResponse.url ?: "",
        urlToString = newsResponse.urlToImage,
        sourceName = newsResponse.source?.name ?: "[blank_source]",
        publishedAt = newsResponse.publishedAt ?: "[blank_date]",
        content = newsResponse.content ?: "[blank_content]",
        isRead = isRead,
        isSearch = isSearch
    )

    fun mapNewsResponsesToEntities(newsResponses: List<NewsResponse>, isRead: Boolean = false, isSearch: Boolean = false): List<NewsEntity> = newsResponses.map {
        mapNewsResponseToEntity(it, isRead, isSearch)
    }
}