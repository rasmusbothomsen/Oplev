package com.example.oplev.data.roomDao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface BaseDao<T> {


        @Insert
        suspend fun insert(item: T)

        @Update
        suspend fun update(item: T)

        @Delete
        suspend fun delete(item: T)
    }
