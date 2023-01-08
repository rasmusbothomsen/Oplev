package com.example.oplev.ViewModel

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.example.oplev.Model.*
import com.example.oplev.data.dataService.CategoryDataService
import com.example.oplev.data.dataService.JourneyDataService
import com.example.oplev.data.dataService.UserDataService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*


class CreateJourneyViewModel(val journeydataService: JourneyDataService, val categoryDataService:CategoryDataService,
                             application: Application
): BaseViewModel<Journey>(application) {

    fun createNewJourney( tag: String, Image: String?, CategoryID: Int, Date: String?, Description: String, Title: String){
        val tempJourney = Journey(UUID.randomUUID().toString(), tag, Image, CategoryID, Date, Description, Title)
        viewModelScope.launch(Dispatchers.IO) {
            journeydataService.insertRoom(tempJourney)
        }
    }

    fun getCategories(): List<Category>{
        val categories = categoryDataService.getAllCategories()
        return categories
    }

}