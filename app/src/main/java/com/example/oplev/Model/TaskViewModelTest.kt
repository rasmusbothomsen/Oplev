package com.example.oplev.Model

import android.app.Application
import android.content.ClipData.Item
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.oplev.data.AppDatabase
import com.example.oplev.data.dataService.JourneyDataService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class TaskViewModelTest(application: Application):AndroidViewModel(application) {
    private val dataService:JourneyDataService
    init {
        dataService = JourneyDataService(AppDatabase.getInstance(application).JourneyDao())

    }
    fun testingAsync(item:Journey){
    viewModelScope.launch(Dispatchers.IO) {
        dataService.insertRoom(item)
        }
    }
    suspend fun getAll(): List<Journey> {
        // Launch a new coroutine and return a Deferred object
        return viewModelScope.async(Dispatchers.IO) {
            // Calculate the result asynchronously
            dataService.getAll()
        }.await()
    }
}