package com.example.oplev

import com.example.oplev.data.dto.CategoryDto
import com.example.oplev.data.dto.JourneyDto
import com.example.oplev.Model.Category
import com.example.oplev.Model.Journey
import com.example.oplev.data.dto.FrontpageDto

//import com.example.oplev.Model.Journey

class DependencyController {
    var categoryData = CategoryDto(null)
    var journeyData = JourneyDto(null)
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
        val journey1 = Journey(1,"img_denmark",null,1,title ="Danmark", description = "Danmark", date = null)
        val journey2 = Journey(1,"img_norway",null,1,title="Norge",description="Norge", date = null)
        val journey3 = Journey(1,"img_finland",null,1,title="Finland",description="Finland", date = null)
        val journey4 = Journey(1,"img_turkey",null,1,title="Tyrkiet",description="Tyrkiet", date = null)
        val journeys = listOf(journey1, journey2, journey3, journey4)

        for (journey in journeys){
            categoryData.journeys.add(journey)
        }

    }
    fun initializeCategorydata(){
        val journeys = categoryData.journeys
        val seneste = CategoryDto(Category(1,"Seneste"))
        val favoritter = CategoryDto(Category(2,"Favoritter"))
        val mumsesteg = CategoryDto(Category(3,"Tester"))
        val categories = listOf(seneste,favoritter, mumsesteg)

        for (category in categories){
            frontpageData.categories.add(category)
        }
    }


}

