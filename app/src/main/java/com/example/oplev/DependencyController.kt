package com.example.oplev

import com.example.oplev.data.dto.CategoryDto
import com.example.oplev.data.dto.JourneyDto
import com.example.oplev.Model.Category
import com.example.oplev.Model.Journey
import com.example.oplev.data.dto.FrontpageDto

//import com.example.oplev.Model.Journey

class DependencyController {
    var categoryData = CategoryDto()
    var journeyData = JourneyDto()
    var frontpageData = FrontpageDto()

    fun initializeData() {
        /* TODO */
/*
        initiazileJournyData()
        initializeCategorydata()

 */
    }

    fun getCategoryDataDependency(): CategoryDto {
        return categoryData
    }

    fun getJourneyDataDependency(): JourneyDto {
        return journeyData
    }

    /*
    This Method is used to initialize journeydata, to keep the dependency across the app.
    Mainly used to test data before database connection
     */

    fun initiazileJournyData(){
        val journey1 = Journey("e","img_denmark",1,null,"Danmark", "Danmark")
        val journey2 = Journey("e","img_norway",2,null,"Norge","Norge")
        val journey3 = Journey("e","img_finland",3,null,"Finland","Finland")
        val journey4 = Journey("e","img_turkey",1,null,"Tyrkiet","Tyrkiet")
        val journeys = listOf(journey1, journey2, journey3, journey4)

        for (journey in journeys){
            categoryData.journeys.add(journey)
        }

    }
    fun initializeCategorydata(){
        val journeys = categoryData.journeys
        val seneste = Category(1, "Seneste", journeys)
        val favoritter = Category(2, "Favoritter", journeys)
        val mumsesteg = Category(3, "mumsesteg", journeys)
        val categories = listOf(seneste,favoritter, mumsesteg)

        for (category in categories){
            frontpageData.categories.add(category)
        }
    }


}

