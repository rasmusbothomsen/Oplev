package com.example.oplev.data.dataService

import com.example.oplev.Model.ImageInfo
import com.example.oplev.data.roomDao.BaseDao
import com.example.oplev.data.roomDao.ImageDao

class ImageDataService(
                       queueDataService: QueueDataService, val imageDao: ImageDao
) :BaseDataService<ImageInfo>(imageDao, queueDataService) {
    suspend fun insertImage(imageInfo: ImageInfo){
        baseDao.insert(imageInfo)
    }

    fun getImageFromId(id:String):ImageInfo{
        return imageDao.getFromId(id)
    }
}