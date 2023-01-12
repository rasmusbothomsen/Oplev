package com.example.oplev.data.roomDao

import androidx.room.Dao
import androidx.room.Query
import com.example.oplev.Model.Folder
import com.example.oplev.Model.Idea
import com.example.oplev.data.dto.baseDto

@Dao
interface FolderDao:BaseDao<Folder> {
    @Query("select*from Folder where journeyId == :journeyId")
    fun findFolderFromJourneyId(journeyId:Int):List<Folder>
    @Query("select * from folder where id == :stringid")
    fun getfromId(stringid:String):List<Folder>
}