package com.example.oplev.data.roomDao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.selects.select

@Dao
interface BaseDao<T> {

    @Insert
    suspend fun insert(item:T)
    @Update
    fun update(item: T)
    @Delete
    fun delete(item: T)
}