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
        viewModelScope.launch(Dispatchers.IO) {
            var journeys = journeyDataService.getJourneys(categoryDataService)
            for (Journey in journeys) {
                journeyDataService.insertItem(Journey)
            }
        }
    }

    fun updateCategory(Id: String, title: String, createdBy: String){
        var tempCategory = Category(Id, title, createdBy)

        runBlocking {
            categoryDataService.updateItem(tempCategory)
        }
    }

    fun setCurrentCategory(category: CategoryDto){
         state.value.copy(currentCategory = category)
    }

    fun setEditCategory(){
        state.value.copy(editCategory = true)
    }

    suspend fun createCategory(title: String, activity: Activity) {
        categoryDataService.createCategory(title, activity)
    }

    fun getCategories(): List<CategoryDto> {
        var id = Firebase.auth.currentUser?.uid
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

}