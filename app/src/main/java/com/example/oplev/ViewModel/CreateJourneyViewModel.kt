package com.example.oplev.ViewModel

import com.example.oplev.Model.*
import com.example.oplev.data.dataService.CategoryDataService
import com.example.oplev.data.dataService.JourneyDataService
import com.example.oplev.data.dataService.UserDataService


class CreateJourneyViewModel(val journeydataService: JourneyDataService,  val categoryDataService:CategoryDataService): BaseViewModel<Journey>() {

    fun createNewJourney(Id: Int, tag: String, Image: String?, CategoryID: Int, Date: String?, Description: String, Title: String){
        val tempJourney = Journey(Id, tag, Image, CategoryID, Date, Description, Title)
        journeydataService.insertRoom(tempJourney)
    }

    fun getCategories(): List<Category>{
        val categories = categoryDataService.getAllCategories()
        return categories
    }

}