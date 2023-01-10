package com.example.oplev.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.oplev.Model.*
import com.example.oplev.data.roomDao.*

@Database(entities = [Category::class, Journey::class, Folder::class, Idea::class, UserInfo::class,QueueItem::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun CategoryDao(): CategoryDao
    abstract fun JourneyDao(): JourneyDao
    abstract fun FolderDao(): FolderDao
    abstract fun IdeaDao(): IdeaDao
    abstract fun UserDao(): UserDao
    abstract fun QueDao():QueDao

    companion object {
        private var instance: AppDatabase? = null


        @Synchronized
        fun getInstance(ctx: Context): AppDatabase {
            if (instance == null)
                instance = Room.databaseBuilder(
                    ctx.applicationContext, AppDatabase::class.java,
                    "oplev_DataBase"
                ).allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build()

            return instance!!

        }


        private val roomCallback = object : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
            }
        }
    }
}
