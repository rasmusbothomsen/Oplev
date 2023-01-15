package com.example.oplev.data.dataService

import android.util.Log
import com.example.oplev.Model.Idea
import com.example.oplev.data.roomDao.BaseDao
import com.example.oplev.data.roomDao.IdeaDao
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class IdeaDataService(
    val dao:IdeaDao, queueDataService: QueueDataService,
): BaseDataService<Idea>(dao, queueDataService) {


    fun findIdea(id:String): Idea{
        return dao.findIdea(id)
    }

    fun getJourneyId(id:String):String{
        return dao.getJourneyFromFolderId(id)
    }


}

