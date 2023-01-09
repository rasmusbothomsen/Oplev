package com.example.oplev.ViewModel

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.example.oplev.Model.Idea
import com.example.oplev.Model.States
import com.example.oplev.data.dataService.IdeaDataService
import com.google.firebase.firestore.FirebaseFirestore

class IdeaViewModel (val dataService: IdeaDataService, application: Application):BaseViewModel<Idea>(
    application)  {
    private var db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val _state = mutableStateOf(States())
    val state: State<States> = _state

    fun getIdeaTitle(): String?{
        val ideaTitle = state.value.chosenIdeaState?.title

        return ideaTitle
    }
}