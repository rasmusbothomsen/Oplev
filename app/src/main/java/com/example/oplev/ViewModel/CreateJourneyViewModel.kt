package com.example.oplev.ViewModel
import android.util.Log
import com.example.oplev.Model.*
import com.example.oplev.Data.*


class CreateJourneyViewModel {
    var journey = JourneyData()
    var category = CategoryData()


    fun createNewJourney(destination : String, category: String, beskrivelse: String){
        Log.d("beskrivelse",beskrivelse)
        Log.d("destination",destination)
        Log.d("category",category)
        journey.journeys.add(Journey("","",null, beskrivelse,"",null,null))
    }
    fun getCategories():MutableList<Category> {
        return category.categorys
    }
}