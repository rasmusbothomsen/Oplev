package com.example.oplev.ViewModel

import android.app.Application
import android.media.Image
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
    var idea = state.value.chosenIdeaState

    fun getIdeaTitle(): String{
        idea?.title.let {
            return it!!
        }
        return ""
    }
    fun createnewIdea(){
        dataService
    }
    fun getIdeaDescription(): String{
        idea?.description.let {
            return it!!
        }
        return ""
    }
    fun getIdeaLink(): String{
        idea?.link.let {
            return it!!
        }
        return ""
    }
    fun getIdeaImage(): String{
        idea?.image.let {
            return it!!
        }
        return ""
    }
    fun getIdeaDate(): String{
        idea?.date.let {
            return it!!
        }
        return ""
    }




}