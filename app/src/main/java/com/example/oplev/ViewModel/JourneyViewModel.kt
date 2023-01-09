package com.example.oplev.ViewModel

import android.app.Activity
import android.app.Application
import android.content.Context
import android.icu.text.CaseMap.Title
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.example.oplev.Model.Journey
import com.example.oplev.Model.States
import com.example.oplev.data.dataService.JourneyDataService
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class JourneyViewModel(val journeyDataService: JourneyDataService, application: Application): BaseViewModel<Journey>(application) {
    private var db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val _state = mutableStateOf(States())
    val state: State<States> = _state

    fun getJourneyTitle(): String?{
        val journeyTitle = state.value.chosenJourneyState?.title

        return journeyTitle
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