package com.example.oplev.data.roomDao

import androidx.room.*


@Dao
interface BaseDao<T> {


        @Insert(onConflict = OnConflictStrategy.REPLACE)
        suspend fun insert(item: T)

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        suspend fun insertAll(items: List<T>)

        @Update
        suspend fun update(item: T)

        @Delete
        suspend fun delete(item: T)
    }


