package com.example.oplev

import com.example.oplev.data.dto.CategoryDto
import com.example.oplev.data.dto.JourneyDto
import com.example.oplev.Model.Category
import com.example.oplev.Model.Journey
/*
class DependencyController {
     var categoryData = CategoryDto()
     var journeyData = JourneyDto()

    fun initializeData(){
        /* TODO */

        initiazileJournyData()
        initializeCategorydata()
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
        val journey1 = Journey("e","img_denmark",null,"","Danmark", null)
        val journey2 = Journey("e","img_norway",null,"","Norge",null)
        val journey3 = Journey("e","img_finland",null,"","Finland",null)
        val journey4 = Journey("e","img_turkey",null,"","Tyrkiet",null)
        val journeys = listOf(journey1, journey2, journey3, journey4)

        for (journey in journeys){
            journeyData.journeys.add(journey)
        }

    }
    fun initializeCategorydata(){
        val journeys = journeyData.journeys
        val seneste = Category("Seneste", journeys)
        val favoritter = Category("Favoritter", journeys)
        val mumsesteg = Category("mumsesteg", journeys)
        val categories = listOf(seneste,favoritter, mumsesteg)

        for (category in categories){
            categoryData.categorys.add(category)
        }
    }
}

 */