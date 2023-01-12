package com.example.oplev.data.roomDao

import androidx.room.*
import kotlinx.coroutines.selects.select

@Dao
interface BaseDao<T> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item:T)
    @Update
    suspend fun update(item: T)
    @Delete
    fun delete(item: T)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun insertAll(vararg item:T)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllAny(items:List<out T>)
}