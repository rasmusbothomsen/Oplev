package com.example.oplev.data.dataService

import com.example.oplev.Model.Journey
import com.example.oplev.data.roomDao.BaseDao
import com.example.oplev.data.roomDao.JourneyDao

class JourneyDataService(
    val dao:JourneyDao
): BaseDataService<Journey>(dao) {

    suspend fun getAll():List<Journey>{
        return dao.getAll()
    }


}