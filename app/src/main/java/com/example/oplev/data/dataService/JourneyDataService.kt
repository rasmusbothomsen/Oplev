package com.example.oplev.data.dataService

import android.util.Log
import com.example.oplev.MainActivity
import com.example.oplev.Model.Folder
import com.example.oplev.Model.Idea
import com.example.oplev.Model.Journey
import com.example.oplev.ViewModel.AuthViewModel
import com.example.oplev.data.AppDatabase
import com.example.oplev.data.roomDao.BaseDao
import com.example.oplev.data.roomDao.JourneyDao
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class JourneyDataService(
    val dao:JourneyDao, queueDataService: QueueDataService,
): BaseDataService<Journey>(dao, queueDataService) {

    override suspend fun insertRoom(item: Journey) {
        val add = HashMap<String,Any>()
        add["id"] = item.id
        add["createdBy"] = Firebase.auth.currentUser?.uid.toString()
        add["category"] = item.categoryID
        add["description"] = item.description
        add["lastEdit"] = Firebase.auth.currentUser?.uid.toString()

        db.collection("journeys")
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

    fun getFolders(id:String):List<Folder>{
        return dao.getAllfoldersFromId(id)
    }

    fun getAbseluteParentFolder(id:String):Folder{
        return dao.getAbseluteParentFolder(id)
    }

    fun getIdeasFromFolder(id:String):List<Idea>{
        return dao.getIdeasFromFolder(id)
    }

    fun findSingle(id:String):Journey{
        return dao.findSingle(id)
    }



}