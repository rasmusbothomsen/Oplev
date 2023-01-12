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

    override suspend fun insertRoom(item: Folder) {
        val add = HashMap<String,Any>()
        add["id"] = item.id
        add["journeyId"] = item.journeyId
        add["parentFolderId"] = item.parentFolderId
        add["title"] = item.title
        add["lastEdit"] = Firebase.auth.currentUser?.uid.toString()

        db.collection("folders")
            .document(Firebase.auth.currentUser?.uid.toString())
            .set(add)
            .addOnCompleteListener(){
                    task ->
                if(task.isSuccessful){
                    Log.d("FirebaseInsert",  "STATUS: SUCCESS")
                }else{

                   // insertQueueItem(item,item.id)
                }
            }

        super.insertRoom(item)
    }

}