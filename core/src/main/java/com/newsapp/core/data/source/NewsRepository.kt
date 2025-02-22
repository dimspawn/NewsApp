package com.newsapp.core.data.source

import android.util.Log
import com.newsapp.core.data.NetworkBoundResource
import com.newsapp.core.data.Resource
import com.newsapp.core.data.source.local.LocalDataSource
import com.newsapp.core.data.source.remote.RemoteDataSource
import com.newsapp.core.data.source.remote.network.ApiResponse
import com.newsapp.core.data.source.remote.response.NewsResponse
import com.newsapp.core.di.CoreScope
import com.newsapp.core.domain.model.NewsData
import com.newsapp.core.domain.model.RefreshData
import com.newsapp.core.domain.repository.INewsRepository
import com.newsapp.core.utils.Constants
import com.newsapp.core.utils.DataMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@CoreScope
class NewsRepository @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
): INewsRepository{
    override fun getMainNews(): Flow<Resource<List<NewsData>>> = object: NetworkBoundResource<List<NewsData>, List<RefreshData>, List<NewsResponse>>() {
        override fun loadFromDbFirst(): Flow<List<RefreshData>> {
            return localDataSource.getRefreshByEntityName(Constants.NEWS_DURATION).map { DataMapper.mapRefreshEntitiesToDomains(it) }
        }

        override fun loadFromDb(): Flow<List<NewsData>> {
            return localDataSource.getMainNews(false).map { DataMapper.mapNewsEntitiesToDomains(it) }
        }

        override fun shouldFetch(data: List<NewsData>, inspect: List<RefreshData>): Boolean {
            return if (inspect.isNotEmpty()) System.currentTimeMillis() > inspect[0].duration + inspect[0].period * 3600 else true
        }

        override fun createCall(
            data: List<NewsData>,
            inspect: List<RefreshData>
        ): Flow<ApiResponse<List<NewsResponse>>> {
            return remoteDataSource.getMainNews()
        }

        override suspend fun saveCallResult(data: List<NewsResponse>) {
            val entities = DataMapper.mapNewsResponsesToEntities(data)
            for (entity in entities) {
                if (entity.id != "") {
                    val localNews = localDataSource.getNewsById(entity.id)
                    if (localNews.isEmpty()) {
                        localDataSource.insertNews(entity)
                    } else {
                        entity.isRead = localNews[0].isRead
                        entity.isSearch = false
                        localDataSource.updateNews(entity)
                    }
                }
            }
        }
    }.asFlow()

    override fun setReadNews(news: NewsData): Flow<Resource<String>> = flow{
        emit(Resource.Loading())
        try {
            localDataSource.updateReadNews(news.id, true)
            emit(Resource.Success(news.url))
        } catch (e: Exception) {
            emit(Resource.Error(e.message.toString()))
        }
    }.flowOn(Dispatchers.IO)

    override fun getReadNews(): Flow<Resource<List<NewsData>>>  = flow {
        emit(Resource.Loading())
        try {
            val data = localDataSource.getReadNewsNoFlow().map { DataMapper.mapNewsEntityToDomain(it) }
            emit(Resource.Success(data))
        } catch (e: Exception) {
            Log.e("NewsRepository", e.message.toString())
            emit(Resource.Error(e.message.toString()))
        }
    }.flowOn(Dispatchers.IO)

    override fun setSearchNews(query: String): Flow<Resource<List<NewsData>>> = object : NetworkBoundResource<List<NewsData>, List<NewsData>, List<NewsResponse>>() {
        override fun loadFromDbFirst(): Flow<List<NewsData>> {
            return localDataSource.getMainNews(true).map { DataMapper.mapNewsEntitiesToDomains(it) }
        }

        override fun loadFromDb(): Flow<List<NewsData>> {
            return localDataSource.getMainNews(true).map { DataMapper.mapNewsEntitiesToDomains(it) }
        }

        override fun shouldFetch(data: List<NewsData>, inspect: List<NewsData>): Boolean = true

        override fun createCall(
            data: List<NewsData>,
            inspect: List<NewsData>
        ): Flow<ApiResponse<List<NewsResponse>>> = remoteDataSource.getSearchNews(query)

        override suspend fun saveCallResult(data: List<NewsResponse>) {
            val entities = DataMapper.mapNewsResponsesToEntities(data, isSearch = true)
            for (entity in entities) {
                if (entity.id != "") {
                    val localNews = localDataSource.getNewsById(entity.id)
                    if (localNews.isEmpty()) {
                        localDataSource.insertNews(entity)
                    } else {
                        entity.isRead = localNews[0].isRead
                        entity.isSearch = true
                        localDataSource.updateNews(entity)
                    }
                }
            }
        }
    }.asFlow()
}