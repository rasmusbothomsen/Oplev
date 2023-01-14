package com.example.oplev.ViewModel

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.example.oplev.Model.Folder
import com.example.oplev.data.dto.JourneyDto
import com.example.oplev.Model.Idea
import com.example.oplev.Model.Journey
import com.example.oplev.data.dataService.IdeaDataService
import com.example.oplev.data.dataService.QueueDataService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.UUID

class CreateIdeaViewModel (val dataService: IdeaDataService, application: Application,val folderId:String): BaseViewModel<Idea>(
    application) {

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
}