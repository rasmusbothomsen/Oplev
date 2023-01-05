package com.example.oplev.ViewModel

import com.example.oplev.data.dto.JourneyDto
import com.example.oplev.Model.Idea
import com.example.oplev.data.dto.IdeaDto

class CreateIdeaViewModel():
    BaseViewModel<Idea>(){

    var idea = readItems()

    fun createNewIdea(ID: Int, Title: String, Description: String, link: String?, Image: String?){
        val tempIdea = Idea(ID, Title, Description, link, Image)
        create(tempIdea)
    }
}