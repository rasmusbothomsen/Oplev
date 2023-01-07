package com.example.oplev.data.dataService

import com.example.oplev.data.roomDao.BaseDao

open class BaseDataService<T> (
     val baseDao: BaseDao<T>
        ){

    fun insertRoom(item: T){
        baseDao.insert(item)
    }

    fun updateItem(item:T){
        baseDao.update(item)
    }
    fun deleteItem(item:T){
        baseDao.delete(item)
    }
    fun getAll(item:T){
        baseDao
    }
}