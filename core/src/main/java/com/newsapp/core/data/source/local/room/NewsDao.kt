package com.newsapp.core.data.source.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.newsapp.core.data.source.local.entity.NewsEntity
import com.newsapp.core.data.source.local.entity.RefreshEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNews(news: NewsEntity)

    @Query("SELECT * FROM news WHERE id = :id LIMIT 1")
    suspend fun getNewsById(id: String): List<NewsEntity>

    @Query("SELECT * FROM news WHERE isSearch = :isSearch")
    fun getMainNews(isSearch: Boolean): Flow<List<NewsEntity>>

    @Query("SELECT * FROM news WHERE isRead = :isRead")
    fun getReadNewsNoFlow(isRead: Boolean): List<NewsEntity>

    @Query("SELECT * FROM refreshes WHERE entityName = :entityName")
    fun getRefreshByEntityName(entityName: String): Flow<List<RefreshEntity>>

    @Update
    suspend fun updateNews(news: NewsEntity)

    @Query("UPDATE news SET isRead = :isRead WHERE id = :id")
    suspend fun updateReadNews(id: String, isRead: Boolean)

    @Query("UPDATE news SET isSearch = :isSearch")
    suspend fun modifyAllSearchNews(isSearch: Boolean)

    @Query("DELETE FROM news WHERE isRead = :isRead")
    suspend fun deleteAllNews(isRead: Boolean)
}