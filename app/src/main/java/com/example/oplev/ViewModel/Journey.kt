package com.example.oplev.ViewModel

import com.example.oplev.Model.Folder
import com.example.oplev.Model.Idea
import com.example.oplev.Model.Journey
import com.example.oplev.Data.JourneyData
import java.util.Date

class Journey {
    private var save = JourneyData()

    fun createIdea(title: String, description: String, link: String?, image: String?){
        var idea = Idea(title, description, link, image)
        save.ideas.add(idea)
    }

    fun createFolder(idea: Idea, folder: Folder, title: String){
        var folder = Folder(idea, folder, title)
        save.folders.add(folder)
    }

    fun createJourney(tag: String, image: String, date: Date, description: String, title: String, folder: Folder){
        var journey = Journey(tag, image, date, description,title,folder)
        save.journeys.add(journey)
    }
}