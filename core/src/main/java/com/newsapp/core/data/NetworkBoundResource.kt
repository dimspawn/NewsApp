package com.newsapp.core.data

import com.newsapp.core.data.source.remote.network.ApiResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

abstract class NetworkBoundResource<ResultType, InspectType, ResponseType> {
    private var result: Flow<Resource<ResultType>> = flow {
        emit(Resource.Loading())
        prepare()
        val dbInspect = loadFromDbFirst().first()
        val dbSource = loadFromDb().first()
        if (shouldFetch(dbSource, dbInspect)) {
            prepareForFetch(dbSource, dbInspect)
            emit(Resource.Loading(dbSource))
            when(val apiResponse = createCall(dbSource, dbInspect).first()) {
                is ApiResponse.Success -> {
                    saveCallResult(apiResponse.data)
                    emitAll(loadFromDb().map { Resource.Success(it) })
                }
                is ApiResponse.Error -> {
                    onFetchFailed()
                    emit(Resource.Error(apiResponse.errorMessage, dbSource))
                }
            }
        } else {
            emitAll(loadFromDb().map { Resource.Success(it) })
        }
    }

    protected open suspend fun prepare() {}
    protected open fun onFetchFailed() {}
    protected abstract fun loadFromDbFirst(): Flow<InspectType>
    protected abstract fun loadFromDb(): Flow<ResultType>
    protected abstract fun shouldFetch(data: ResultType, inspect: InspectType): Boolean
    protected open suspend fun prepareForFetch(data: ResultType, inspect: InspectType) {}
    protected abstract fun createCall(data: ResultType, inspect: InspectType): Flow<ApiResponse<ResponseType>>
    protected abstract suspend fun saveCallResult(data: ResponseType)
    fun asFlow(): Flow<Resource<ResultType>> = result
}