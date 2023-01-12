package com.example.oplev.data.roomDao

import androidx.room.Dao
import androidx.room.Query
import com.example.oplev.Model.Folder
import com.example.oplev.Model.Idea
import com.example.oplev.Model.Journey

@Dao
interface IdeaDao:BaseDao<Idea> {

    @Query("Select * from ideas where folderId == :folderId")
    fun getIdeaFromFolderID(folderId:Int):List<Idea>
    @Query("select * from ideas where id == :stringid")
    fun getfromId(stringid:String):List<Idea>

    @Query("select * from ideas where id == :id")
    fun findIdea(id:String): Idea
}