package com.example.oplev.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.oplev.Model.*
import com.example.oplev.data.roomDao.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Database(entities = [Category::class, Journey::class, Folder::class, Idea::class, UserInfo::class,QueueItem::class], version = 3)
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
            if (instance == null) {
                instance = Room.databaseBuilder(
                    ctx.applicationContext, AppDatabase::class.java,
                    "oplev_DataBase"
                ).allowMainThreadQueries().fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build()
            }


            return instance!!

        }



        private val roomCallback = object : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)

            }

        }
        fun CreateDummyData(){
            instance?.clearAllTables()
            var userId = Firebase.auth.currentUser?.uid.toString()

            val idea1 = Idea(
                id = "1",
                journeyId = "123",
                folderId = "1",
                title = "Idea 1",
                description = "This is the description for Idea 1",
                link = "www.idea1.com",
                image = "img_japan",
                date = "1"
            )


            val idea2 = Idea(
                id = "2",
                folderId = "2",
                journeyId = "123",
                title = "Idea 2",
                description = "This is the description for Idea 2",
                link = "www.idea2.com",
                image = "img_india",
                date = "1"


            )


            val idea3 = Idea(
                id = "3",
                journeyId = "123",
                folderId = "2",
                title = "Idea 3",
                description = "This is the description for Idea 3",
                link = "www.google.com",
                image = "img_japan",
                date = "1"
            )


            val folder1 = Folder(
                id = "1",
                journeyId = "1",
                parentFolderId = "1",
                title = "Folder 1"
            )

            val folder2 = Folder(
                id = "2",
                journeyId = "1",
                parentFolderId = "1",
                title = "Folder 2"
            )

            val folder3 = Folder(
                id = "3",
                journeyId = "2",
                parentFolderId = "3",
                title = "Folder 3"
            )



            val category1 = Category(
                id = "1",
                title = "Category 1",
                createdBy = userId
            )

            val category2 = Category(
                id = "2",
                title = "Category 2",
                createdBy = userId
            )

            val journey1 = Journey(
                id = "1",
                tag = "journey1",
                image = "img_japan",
                categoryID = "1",
                date = "2020-01-01",
                description = "This is the description for Journey 1",
                title = "Journey 1"
            )

            val journey2 = Journey(
                id = "2",
                tag = "journey2",
                image = "img_india",
                categoryID = "2",
                date = "2022-01-01",
                description = "This is the description for Journey 2",
                title = "Journey 2"
            )

            instance?.CategoryDao()?.insertAll(category1,category2)
            instance?.IdeaDao()?.insertAll(idea2,idea1,idea3)
            instance?.JourneyDao()?.insertAll(journey1,journey2)
            instance?.FolderDao()?.insertAll(folder1,folder2, folder3)

        }
    }
}
