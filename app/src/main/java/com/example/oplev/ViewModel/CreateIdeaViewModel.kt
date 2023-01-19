package com.example.oplev.ViewModel

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.example.oplev.Model.Folder
import com.example.oplev.data.dto.JourneyDto
import com.example.oplev.Model.Idea
import com.example.oplev.Model.Journey
import com.example.oplev.Model.States
import com.example.oplev.data.dataService.IdeaDataService
import com.example.oplev.data.dataService.QueueDataService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.UUID

class CreateIdeaViewModel (val dataService: IdeaDataService, application: Application,val folderId:String): BaseViewModel<Idea>(
    application) {

    fun createNewIdea(title: String, description: String, link: String, image: String, date: String) {
        val journeyId = dataService.getJourneyId(folderId)
        val idea = Idea(
            UUID.randomUUID().toString(),
            journeyId,
            folderId,
            title,
            description,
            link,
            image,
            date
        )
        viewModelScope.launch(Dispatchers.IO)  {
            dataService.insertItem(idea)
        }
    }
}