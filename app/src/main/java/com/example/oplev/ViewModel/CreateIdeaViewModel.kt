package com.example.oplev.ViewModel

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.example.oplev.Model.Folder
import com.example.oplev.data.dto.JourneyDto
import com.example.oplev.Model.Idea
import com.example.oplev.Model.Journey
import com.example.oplev.Model.States
import com.example.oplev.data.dataService.IdeaDataService
import com.example.oplev.data.dataService.QueueDataService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.UUID

class CreateIdeaViewModel (val dataService: IdeaDataService, application: Application, val folderId: String, val ideaId: String?): BaseViewModel<Idea>(
    application) {

    val _state = mutableStateOf(States())
    val state: State<States> = _state

    init {
        if(ideaId != null){
            _state.value = state.value.copy(editIdea = true)
        }
    }

    fun createNewIdea(title: String, description: String, link: String, image: String, date: String) {
        val idea = Idea(
            UUID.randomUUID().toString(),
            folderId,
            title,
            description,
            link,
            image,
            date
        )
        runBlocking {
            dataService.insertItem(idea)
        }
    }

    fun getIdea(Id: String): Idea{
        var idea = dataService.findIdea(Id)

        return idea
    }

    fun updateIdea(Id: String, title: String, description: String, link: String, image: String, date: String){
        var tempIdea = Idea(Id, folderId, title, description, link, image, date)

        runBlocking {
            dataService.updateItem(tempIdea)
        }
    }
}