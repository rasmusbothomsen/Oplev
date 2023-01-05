package com.example.oplev.ViewModel

import com.example.oplev.Model.*



class CreateJourneyViewModel():
    BaseViewModel<Journey>() {

    var journey = readItems()

    fun createNewJourney(Id: Int, tag: String, Image: String?, CategoryID: Int, Date: String?, Description: String, Title: String){
        val tempJourney = Journey(Id, tag, Image, CategoryID, Date, Description, Title)
        create(tempJourney)
    }


    fun getCategories(): List<Category>{
        // Kan dette lade sig gøre i og med at BaseViewModel<Journey> i extension?
    }

}