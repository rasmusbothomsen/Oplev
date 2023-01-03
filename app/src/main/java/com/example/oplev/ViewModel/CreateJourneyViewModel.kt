package com.example.oplev.ViewModel
import android.util.Log
import com.example.oplev.Model.*
import com.example.oplev.data.*
import com.example.oplev.data.dto.CategoryDto
import com.example.oplev.data.dto.FrontpageDto
import com.example.oplev.data.dto.JourneyDto


class CreateJourneyViewModel(var journey: JourneyDto, var categoryData: CategoryDto, var frontpageDto: FrontpageDto) {



    fun createNewJourney(destination : String, category: String, beskrivelse: String){
        Log.d("beskrivelse",beskrivelse)
        Log.d("destination",destination)
        Log.d("category",category)
        var tempJourney = Journey(tag ="e",image = "img_finland", categoryID = 1, description = beskrivelse, date = null, title = "test")
        categoryData.journeys.add(tempJourney)
    }
    fun getCategories():MutableList<Category> {
        return frontpageDto.categories
    }
}