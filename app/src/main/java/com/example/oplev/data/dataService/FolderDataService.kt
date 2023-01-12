package com.example.oplev.data.dataService

import android.util.Log
import com.example.oplev.MainActivity
import com.example.oplev.Model.Folder
import com.example.oplev.Model.Journey
import com.example.oplev.data.collections.FolderCollection
import com.example.oplev.data.dto.FolderDto
import com.example.oplev.data.roomDao.BaseDao
import com.example.oplev.data.roomDao.FolderDao
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class FolderDataService(val dao:FolderDao,queueDataService: QueueDataService):BaseDataService<Folder>(dao,queueDataService) {

    suspend fun createFolder(item : Folder) {
        dao.insert(item)
    }


}