package com.example.oplev.Data.roomDao

import androidx.room.*
import com.example.oplev.Model.testRoom

@Dao
interface testRoomDao {
    @Query("SELECT*From testRoom")
    fun getAll(): List<testRoom>

    @Insert
    fun insert(user: testRoom)

    @Update
    fun update(user: testRoom)

    @Delete
    fun delete(user: testRoom)
}