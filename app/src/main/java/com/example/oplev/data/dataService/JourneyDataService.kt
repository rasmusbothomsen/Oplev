package com.example.oplev.data.dataService

import android.util.Log
import com.example.oplev.MainActivity
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
    private var db : FirebaseFirestore = FirebaseFirestore.getInstance()

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
                    insertQueueItem(item,item.id)
                }
            }

        super.insertRoom(item)
    }


}