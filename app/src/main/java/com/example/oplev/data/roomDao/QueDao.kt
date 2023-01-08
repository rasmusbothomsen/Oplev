package com.example.oplev.data.roomDao

import androidx.room.Dao
import androidx.room.Query
import com.example.oplev.Model.QueueItem

@Dao
interface QueDao:BaseDao<QueueItem> {

    @Query("select * from queue_table")
    fun getAll():List<QueueItem>
}