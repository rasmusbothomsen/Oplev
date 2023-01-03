package com.example.oplev.data.roomDao

import androidx.room.Dao
import androidx.room.Query
import com.example.oplev.Model.Folder

@Dao
interface FolderDao {
    @Query("select*from Folder")
    fun findFolderFromJourneyId():List<Folder>
}