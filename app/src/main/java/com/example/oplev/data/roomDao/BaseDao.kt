package com.example.oplev.data.roomDao

import androidx.room.*
import kotlinx.coroutines.selects.select

@Dao
interface BaseDao<T> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item:T)
    @Update
    fun update(item: T)
    @Delete
    fun delete(item: T)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg item:T)
}