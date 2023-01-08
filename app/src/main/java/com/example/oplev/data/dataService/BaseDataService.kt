package com.example.oplev.data.dataService

import com.example.oplev.Model.Journey
import com.example.oplev.data.roomDao.BaseDao
import kotlin.reflect.typeOf

open class BaseDataService<T> (
     val baseDao: BaseDao<T>,
     val queueDataService: QueueDataService
        ){

   open suspend fun insertRoom(item: T){
        baseDao.insert(item)
    }

    open suspend fun updateItem(item:T){
        baseDao.update(item)
    }
    open suspend fun deleteItem(item:T){
        baseDao.delete(item)
    }
    open suspend fun getAll(item:T){
        baseDao
    }
    open fun insertQueueItem(item:T, itemId:String){
        queueDataService.insertItemToQue(item!!::class.simpleName!!,itemId)
    }

}