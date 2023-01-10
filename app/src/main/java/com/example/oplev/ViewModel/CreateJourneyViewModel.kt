package com.example.oplev.ViewModel

import android.app.Activity
import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.oplev.Model.*
import com.example.oplev.data.dataService.CategoryDataService
import com.example.oplev.data.dataService.JourneyDataService
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.util.*


class CreateJourneyViewModel(val journeydataService: JourneyDataService,  val categoryDataService:CategoryDataService, application: Application): BaseViewModel<Journey>(application) {
    private var db: FirebaseFirestore = FirebaseFirestore.getInstance()

    fun createNewJourney( tag: String, Image: String?, CategoryID: String, Date: String?, Description: String, Title: String){
        var img = Image
        if (img == null){
            // default billede her
            img = "img_paris"
        }
        val tempJourney = Journey(UUID.randomUUID().toString(), tag, img, CategoryID, Date, Description, Title)
        viewModelScope.launch(Dispatchers.IO) {
            journeydataService.insertRoom(tempJourney)
        }
    }

    fun getCategoryIdFromTitle(Title: String): String{
        val categoryID = categoryDataService.getCategoryId(Title)

        return categoryID
    }

    fun getCategories(): List<Category>{
        val categories = categoryDataService.getAllCategories()
        return categories
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