package com.example.oplev.ViewModel

import com.example.oplev.Data.JourneyData
import com.example.oplev.Model.Idea

class CreateIdeaViewModel(var journeyData: JourneyData) {

    fun createNewIdea(titel: String, beskrivelse: String, link: String){

        var tempIdea = Idea(titel, beskrivelse, link, null)
        journeyData.ideas.add(tempIdea)
    }
}