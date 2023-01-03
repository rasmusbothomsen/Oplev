package com.example.oplev.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.oplev.Model.Category
import com.example.oplev.Model.Folder
import com.example.oplev.Model.Journey
import com.example.oplev.data.roomDao.BaseDao
import com.example.oplev.data.roomDao.CategoryDao
import com.example.oplev.data.roomDao.FolderDao
import com.example.oplev.data.roomDao.JourneyDao

@Database(entities = [Category::class, Journey::class, Folder::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun CategoryDao(): CategoryDao
    abstract fun JourneyDao(): JourneyDao
    abstract fun FolderDao(): FolderDao
}
