package com.example.oplev.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.oplev.data.roomDao.BaseDao
import com.example.oplev.data.roomDao.CategoryDao
import com.example.oplev.data.roomDao.JourneyDao

@Database(entities = [CategoryDao::class, JourneyDao::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun CategoryDao(): CategoryDao
    abstract fun JourneyDao(): JourneyDao
}
