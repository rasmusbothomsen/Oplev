package com.example.oplev.data.roomDao

import androidx.room.Dao
import androidx.room.Query
import com.example.oplev.Model.ImageInfo
@Dao
interface ImageDao:BaseDao<ImageInfo> {
    @Query("Select * from ImageInfo where imageId == :id")
    fun getFromId(id:String):ImageInfo
}