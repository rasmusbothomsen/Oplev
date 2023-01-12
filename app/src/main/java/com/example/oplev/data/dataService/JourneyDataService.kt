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
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.runBlocking
import java.util.*
import kotlin.collections.HashMap

class JourneyDataService(
    val dao:JourneyDao, queueDataService: QueueDataService
): BaseDataService<Journey>(dao, queueDataService) {
    //private var db : FirebaseFirestore = FirebaseFirestore.getInstance()


                suspend fun createJourney(item: Journey) {
                    dao.insert(item)
                }


                suspend fun getJourneys(categoryDataService: CategoryDataService): List<Journey> {
                    var categoryDataService = categoryDataService
                    var journeys = mutableListOf<Journey>()
                    var journeyIds = categoryDataService.getSharedJourneyIds()
                    for (ids in journeyIds) {
                        db.collection("journeys")
                            .whereEqualTo("id", ids)
                            .get()
                            .addOnSuccessListener { documents ->
                                for (document in documents) {
                                    journeys.add(
                                        Journey(
                                            document.data["id"] as String,
                                            document.data["tag"] as String,
                                            document.data["image"] as String,
                                            categoryDataService.getCategoryId("Delt med mig"),
                                            document.data["date"] as String,
                                            document.data["description"] as String,
                                            document.data["title"] as String
                                        )
                                    )
                                }
                            }
                    }
                    return journeys
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

