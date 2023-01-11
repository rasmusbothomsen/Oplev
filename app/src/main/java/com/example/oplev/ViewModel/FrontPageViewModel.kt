package com.example.oplev.ViewModel

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.core.os.bundleOf
import com.example.oplev.Model.*
import com.example.oplev.data.dataService.CategoryDataService
import com.example.oplev.data.dataService.UserDataService
import com.example.oplev.data.dto.CategoryDto
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class FrontPageViewModel(application: Application, val categoryDataService: CategoryDataService, val userDataService: UserDataService):BaseViewModel<Category>(
    application
) {
    private var db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val _state = mutableStateOf(States())
    val state: State<States> = _state

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