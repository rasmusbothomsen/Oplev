package com.example.oplev.data.roomDao

import androidx.room.Dao
import androidx.room.Query
import com.example.oplev.Model.Folder
import com.example.oplev.Model.Idea
@Dao
interface IdeaDao:BaseDao<Idea> {

    @Query("Select * from ideas where folderId == :folderId")
    fun getIdeaFromFolderID(folderId:Int):List<Idea>

}