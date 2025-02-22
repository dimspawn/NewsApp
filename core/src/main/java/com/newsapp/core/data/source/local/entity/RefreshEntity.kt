package com.newsapp.core.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "refreshes")
data class RefreshEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,
    @ColumnInfo(name = "entityName")
    val entityName: String,
    @ColumnInfo(name = "duration")
    val duration: Long,
    @ColumnInfo(name = "period")
    val period: Int
)