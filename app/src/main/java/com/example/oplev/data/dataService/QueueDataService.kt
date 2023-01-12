package com.example.oplev.data.dataService

import android.icu.util.TimeUnit
import com.example.oplev.Model.QueueItem
import com.example.oplev.data.AppDatabase
import com.example.oplev.data.roomDao.QueDao
import kotlinx.coroutines.*

class QueueDataService(

    val appDatabase:AppDatabase
) {
    val dao = appDatabase.QueDao()

    fun insertItemToQue(itemType:String,itemID:String){
        val item = QueueItem(0,itemType,itemID,true)
        dao.insert(item)
    }
    suspend fun syncDatabases():Deferred<Unit>{
        return GlobalScope.async(Dispatchers.IO) {
            delay(2000)
        }
    }
    suspend fun upDateFirebaseFromQueue(){
        val items = dao.getAll()
        for (item in items){
            when(item.type)
            {
                "Idea" -> processIdea(item.objectId)
                "Folder" -> processFolder(item.objectId)
                "Category" -> processCategory(item.objectId)
                "Journey" -> processJourney(item.objectId)
            }
        }


    }

    fun processIdea(objectIt:String){

    }fun processFolder(objectIt:String){

    }fun processCategory(objectIt:String){

    }fun processJourney(objectIt:String){

    }



}