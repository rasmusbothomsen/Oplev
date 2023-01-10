package com.example.oplev.ViewModel

import android.app.Activity
import android.app.Application
import android.content.Context
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.example.oplev.Model.Folder
import com.example.oplev.Model.Idea
import com.example.oplev.Model.Journey
import com.example.oplev.Model.States
import com.example.oplev.data.dataService.JourneyDataService
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.tasks.await


class JourneyViewModel(val journeyDataService: JourneyDataService, application: Application, journeyID: String): BaseViewModel<Journey>(application) {
    private var db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val _state = MutableStateFlow(States())
    private val _uiState = MutableStateFlow(JourneyUiState())
    private val currentJourney:Journey

    val uiState:StateFlow<JourneyUiState> = _uiState.asStateFlow()
    val state: StateFlow<States> = _state.asStateFlow()


    init {
        currentJourney = journeyDataService.findSingle(journeyID)
        _uiState.update { currentState ->
            currentState.copy(
                openFolder = journeyDataService.getAbseluteParentFolder(currentJourney.id),
            )
        }
        updateOpenFolder()

    }

    private fun updateOpenFolder(){
        _uiState.update { currentState ->
            currentState.copy(
                openFolder = currentState.openFolder,
                ideas = journeyDataService.getIdeasFromFolder(currentState.openFolder!!.id),
                folders = journeyDataService.getFolders(currentState.openFolder.id)
            )
        }
    }

    fun openNewFolder(folder: Folder){
        _uiState.update { currentState ->
            currentState.copy(
                openFolder = folder,
            )
        }
        updateOpenFolder()
    }



    fun getJourneyTitle(): String{

        currentJourney.title.let {
            return it!!
        }
        return ""
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
data class JourneyUiState(
    val openFolder:Folder? = null,
    val folders:List<Folder> = emptyList(),
    val ideas:List<Idea> = emptyList()

)
