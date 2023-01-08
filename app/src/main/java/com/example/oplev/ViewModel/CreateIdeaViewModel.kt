package com.example.oplev.ViewModel

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.example.oplev.Model.Folder
import com.example.oplev.data.dto.JourneyDto
import com.example.oplev.Model.Idea
import com.example.oplev.data.dataService.IdeaDataService
import com.example.oplev.data.dataService.QueueDataService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.UUID

class CreateIdeaViewModel(val dataService:IdeaDataService, application: Application):BaseViewModel<Idea>(
    application
)  {

    fun createNewIdea(titel: String, beskrivelse: String, link: String){


    }


}