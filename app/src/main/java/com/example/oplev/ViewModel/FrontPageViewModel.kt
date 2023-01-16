package com.example.oplev.ViewModel

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.core.os.bundleOf
import androidx.lifecycle.viewModelScope
import com.example.oplev.Model.*
import com.example.oplev.data.dataService.CategoryDataService
import com.example.oplev.data.dataService.JourneyDataService
import com.example.oplev.data.dataService.UserDataService
import com.example.oplev.data.dto.CategoryDto
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await

class FrontPageViewModel(application: Application, val categoryDataService: CategoryDataService, val journeyDataService: JourneyDataService ,val userDataService: UserDataService):BaseViewModel<Category>(
    application
) {
    private var db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val _state = mutableStateOf(States())
    val state: State<States> = _state


     suspend fun updateFrontPage() {
            var journeys = journeyDataService.getJourneys(categoryDataService)
             changeupdatedStat()
         Log.d("FrontPageUpdate","Called")

     }

    suspend fun createCategory(title: String, activity: Activity) {
        categoryDataService.createCategory(title, activity)
    }

    suspend fun getCategories(): List<CategoryDto> {
        var id = Firebase.auth.currentUser?.uid
       // updateFrontPage()
        var categorylist = categoryDataService.getCategoryDto(id.toString())

        return categorylist
    }

    fun createNavBundleJourney(journey: Journey): Bundle {
        return bundleOf("journeyId" to journey.id)
    }


    fun setCategories(categories: List<Category>?) {
        /* TODO */
    }

    suspend fun getUserName(activity: Activity, baseContext: Context): String {
        return userDataService.getFirstname()
    }

    suspend fun signOut(){
        Firebase.auth.signOut()
    }

    fun expandFab(){
        val currentValue = state.value.fabExpanded
        _state.value = _state.value.copy(fabExpanded = !currentValue)
    }

    fun changeDialogVal(){
        val currentValue = state.value.dialogState
        _state.value = _state.value.copy(dialogState = !currentValue)
    }

    fun changeupdatedStat(){
        val currentValue = state.value.frontPageUpdated
        _state.value = _state.value.copy(frontPageUpdated = !currentValue)
    }

    fun logOutdialog(){
        val currentValue = state.value.logOutdialog
        _state.value = _state.value.copy(logOutdialog = !currentValue)
    }

}