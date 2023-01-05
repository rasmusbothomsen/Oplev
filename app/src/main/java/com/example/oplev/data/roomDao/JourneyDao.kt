package com.example.oplev.data.roomDao

import androidx.room.Dao
import androidx.room.Query
import com.example.oplev.Model.Journey
import com.example.oplev.data.dto.baseDto

@Dao
interface JourneyDao {
    @Query("select * from Journey")
    fun getAll(): List<Journey>


}