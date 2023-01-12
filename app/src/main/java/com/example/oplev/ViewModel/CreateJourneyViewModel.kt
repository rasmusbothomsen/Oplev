package com.example.oplev.ViewModel

import android.app.Activity
import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.oplev.Model.*
import com.example.oplev.data.dataService.CategoryDataService
import com.example.oplev.data.dataService.FolderDataService
import com.example.oplev.data.dataService.JourneyDataService
import com.example.oplev.data.dataService.UserDataService
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import java.util.*


class CreateJourneyViewModel(val journeydataService: JourneyDataService,  val categoryDataService:CategoryDataService, val userDataService: UserDataService, application: Application, val folderDataService: FolderDataService): BaseViewModel<Journey>(application) {
    private var db: FirebaseFirestore = FirebaseFirestore.getInstance()

    fun createNewJourney( tag: String, Image: String?, CategoryID: String, Date: String?, Description: String, Title: String, collaboratorMail: String, activity: Activity){
        var img = "img_paris"
        val tempJourney = Journey(UUID.randomUUID().toString(), tag, img, CategoryID, Date, Description, Title)
        val baseFolderId = UUID.randomUUID().toString()
        val baseFolderOfJourney = Folder(baseFolderId,tempJourney.id,baseFolderId,"Basefolder")

        runBlocking {
            journeydataService.insertItem(tempJourney)
            folderDataService.insertItem(baseFolderOfJourney)

            if (!collaboratorMail.isEmpty()) {
                shareJourney(
                    Firebase.auth.currentUser?.uid.toString(),
                    tempJourney.id,
                    collaboratorMail,
                    activity
                )
            }
        }
    }

    fun getCategoryIdFromTitle(Title: String): String{
        val categoryID = categoryDataService.getCategoryId(Title)

        return categoryID
    }

    fun getCategories(): List<Category>{
        var id = Firebase.auth.currentUser?.uid
        val categories = categoryDataService.getAllCategories(id.toString())
        return categories
    }

    fun shareJourney(ownerId : String,
                     journeyId : String,
                     collaboratorMail : String,
                     activity: Activity) {
        runBlocking {
            userDataService.shareJourney(
                ownerId,
                journeyId,
                collaboratorMail,
                activity
            )
        }
    }

    suspend fun getUserName(activity: Activity, baseContext: Context): String {
        val docRef = db.collection("users").document(Firebase.auth.currentUser?.uid.toString())

        val userInfo = docRef.get()

            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {

                    Log.d(AuthViewModel.TAG, "retrieveUserName:success")
                } else {
                    Log.d(AuthViewModel.TAG, "retrieveUserName:failed")

                }
            }.await()

        return userInfo["firstname"].toString()

    }

}