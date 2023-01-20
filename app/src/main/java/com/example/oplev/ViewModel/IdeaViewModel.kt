package com.example.oplev.ViewModel

import android.app.Application

import com.example.oplev.Model.Idea
import com.example.oplev.Model.States
import com.example.oplev.data.dataService.IdeaDataService
import com.example.oplev.data.dataService.ImageDataService
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class IdeaViewModel (val ideaDataService: IdeaDataService, application: Application, ideaID: String,
                     imageDataService: ImageDataService
): BaseViewModel(
    application, imageDataService
)  {
    private var db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val _state = MutableStateFlow(States())
    private val _uiState = MutableStateFlow(IdeaUiState())
    val currentIdea: Idea

    val uiState: StateFlow<IdeaUiState> = _uiState.asStateFlow()
    val state: StateFlow<States> = _state.asStateFlow()

    init {
        currentIdea = ideaDataService.findIdea(ideaID)

    }

    fun openIdea(){
        _uiState.update { currentState ->
            currentState.copy(
                openIdea = currentState.openIdea
            )
        }
    }

    }


data class IdeaUiState(
    val openIdea: Idea? = null,
)
