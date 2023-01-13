package com.example.oplev.data.dataService

import android.app.Activity
import android.util.Log
import com.example.oplev.MainActivity
import com.example.oplev.Model.Category
import com.example.oplev.Model.Folder
import com.example.oplev.Model.Idea
import com.example.oplev.Model.Journey
import com.example.oplev.ViewModel.AuthViewModel
import com.example.oplev.ViewModel.AuthViewModel.Companion.TAG
import com.example.oplev.data.AppDatabase
import com.example.oplev.data.roomDao.BaseDao
import com.example.oplev.data.roomDao.CategoryDao
import com.example.oplev.data.roomDao.JourneyDao
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import java.util.*
import kotlin.collections.HashMap

class JourneyDataService(
    val dao:JourneyDao, queueDataService: QueueDataService
): BaseDataService<Journey>(dao, queueDataService) {
    //private var db : FirebaseFirestore = FirebaseFirestore.getInstance()

    override suspend fun insertRoom(item: Journey) {
        val add = HashMap<String, Any>()

        db.collection("journeys")
            .document(Firebase.auth.currentUser?.uid.toString())
            .set(add)
            .addOnCompleteListener() { task ->
                if (task.isSuccessful) {
                    Log.d("FirebaseInsert", "STATUS: SUCCESS")
                } else {
                    runBlocking {
                    insertQueueItem(item, item.id)
                    }
                }
            }
        super.insertItem(item)
    }

                suspend fun createJourney(item: Journey) {
                    dao.insert(item)
                }


                suspend fun getJourneys(categoryDataService: CategoryDataService): List<Journey> {
                    var categoryDataService = categoryDataService
                    var journeys = mutableListOf<Journey>()
                    var jdocuments = arrayListOf<QueryDocumentSnapshot>()
                    var journeyIds = categoryDataService.getSharedJourneyIds()

                        var query = db.collection("users")
                            .document(Firebase.auth.currentUser?.uid.toString())
                            .collection("Journey")
                            .get()
                            .addOnSuccessListener { documents ->
                                for (document in documents) {
                                  jdocuments.add(document)
                                }
                            }.await()

                    for (i in 0 until query.size()){
                        journeys.add(
                            Journey(
                                query.documents.get(i).get("id").toString(),
                                query.documents.get(i).get("tag").toString(),
                                query.documents.get(i).get("image").toString(),
                                query.documents.get(i).get("categoryID").toString(),
                                query.documents.get(i).get("date").toString(),
                                query.documents.get(i).get("description").toString(),
                                query.documents.get(i).get("title").toString(),
                            )
                        )
                    }

                    return journeys
                }

    suspend fun insertSharedJourneyToFirebase(item : Journey, collaboratorId : String, categoryDataService: CategoryDataService, activity: Activity ){
        val categoryDataService = categoryDataService

        val add = HashMap<String, Any>()

        val id = item.id
        val tag = item.tag
        val image = item.image.toString()
        val categoryID = categoryDataService.getCategoryIdFromTitle("Delt med mig", collaboratorId, activity)
        val date = item.date.toString()
        val description = item.description
        val title = item.title

        add["id"] = id
        add["tag"] = tag
        add["image"] = image
        add["categoryID"] = categoryID
        add["date"] = date
        add["description"] = description
        add["title"] = title

        //var sucess = false

        db.collection("users").document(collaboratorId).collection("Journey")
            .document(id)
            .set(add)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    Log.d(AuthViewModel.TAG, "sharing created :success")
                } else {
                    Log.w(
                        AuthViewModel.TAG,
                        "sharing created :failure",
                        task.exception
                    )
                }
            }.await()

       // if (!sucess){
         //   insertQueueItem(item,add["id"].toString())
        //}

    }


                fun getFolders(id: String): List<Folder> {
                    return dao.getAllfoldersFromId(id)
                }

                fun getAbseluteParentFolder(id: String): Folder {
                    return dao.getAbseluteParentFolder(id)
                }

                fun getIdeasFromFolder(id: String): List<Idea> {
                    return dao.getIdeasFromFolder(id)
                }

                fun findSingle(id: String): Journey {
                    return dao.findSingle(id)
                }

            }

