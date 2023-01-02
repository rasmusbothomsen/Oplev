package com.example.oplev.ViewModel
import android.util.Log
import com.example.oplev.Model.*
import com.example.oplev.data.*
import com.example.oplev.data.dto.CategoryDto
import com.example.oplev.data.dto.JourneyDto


class CreateJourneyViewModel(var journey: JourneyDto, var categoryData: CategoryDto) {



    fun createNewJourney(destination : String, category: String, beskrivelse: String){
        Log.d("beskrivelse",beskrivelse)
        Log.d("destination",destination)
        Log.d("category",category)
        var tempJourney = Journey("e","img_finland",null, beskrivelse,destination,null,null)
        journey.journeys.add(tempJourney)
    }
    fun getCategories():MutableList<Category> {
        return categoryData.categorys
    }
}