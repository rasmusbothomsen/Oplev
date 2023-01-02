package com.example.oplev.Data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.oplev.Data.roomDao.testRoomDao
import com.example.oplev.Model.testRoom

@Database(entities = [testRoom::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): testRoomDao
}
