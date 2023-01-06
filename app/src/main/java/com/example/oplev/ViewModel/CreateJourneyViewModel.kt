package com.example.oplev.ViewModel

import com.example.oplev.Model.*
import com.example.oplev.data.dataService.CategoryDataService



class CreateJourneyViewModel(): BaseViewModel<Journey>() {

    fun createNewJourney(Id: Int, tag: String, Image: String?, CategoryID: Int, Date: String?, Description: String, Title: String){
        val tempJourney = Journey(Id, tag, Image, CategoryID, Date, Description, Title)
        create(tempJourney)
    }

    var categoryDataService = CategoryDataService()
    fun getCategories(): List<Category>{
        val categories = categoryDataService.getAllCategories()

        return categories
    }

}