package com.example.oplev.data.roomDao

import androidx.room.Dao
import androidx.room.Query
import com.example.oplev.Model.Folder
import com.example.oplev.Model.Idea
import com.example.oplev.Model.Journey
import com.example.oplev.data.dto.baseDto

@Dao
interface JourneyDao:BaseDao<Journey> {
    @Query("select * from Journey")
    fun getAll(): List<Journey>

    @Query("select * from Folder where parentFolderId == :id and id != parentFolderId")
    fun getAllfoldersFromId(id:String):List<Folder>

    @Query("select * from Folder where journeyId == :journeyID and parentFolderId == id")
    fun getAbseluteParentFolder(journeyID:String):Folder

    @Query("select* from ideas where folderId == :id")
    fun getIdeasFromFolder(id: String):List<Idea>

    @Query("select * from Journey where id == :id")
    fun findSingle(id:String):Journey
    @Query("select * from Journey where id == :stringid")
    fun getfromId(stringid:String):List<Journey>
    @Query("delete from Journey")
    fun deleteAll()
}