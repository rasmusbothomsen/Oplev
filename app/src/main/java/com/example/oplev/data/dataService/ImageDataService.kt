package com.example.oplev.data.dataService

import com.example.oplev.Model.ImageInfo
import com.example.oplev.data.roomDao.BaseDao
import com.example.oplev.data.roomDao.ImageDao
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class ImageDataService(
                       queueDataService: QueueDataService, val imageDao: ImageDao
) :BaseDataService<ImageInfo>(imageDao, queueDataService) {
    val storageRef = Firebase.storage.reference

    suspend fun insertImage(imageInfo: ImageInfo){
        baseDao.insert(imageInfo)
        insertIntoStorage(imageInfo)
    }

        fun insertIntoStorage(imageInfo: ImageInfo){
        val userId = Firebase.auth.currentUser?.uid.toString()
        val imageId = imageInfo.imageId
        val imageRef = storageRef.child("$userId/$imageId")
        val imageBytes = imageInfo.image
        
        imageRef.putBytes(imageBytes)

    }


    fun getImageFromId(id:String):ImageInfo{
        return imageDao.getFromId(id)
    }
}