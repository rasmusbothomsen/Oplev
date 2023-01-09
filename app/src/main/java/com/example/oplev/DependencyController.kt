package com.example.oplev

import android.app.Application
import com.example.oplev.ViewModel.AuthViewModel
import com.example.oplev.ViewModel.CreateJourneyViewModel
import com.example.oplev.ViewModel.FrontPageViewModel
import com.example.oplev.ViewModel.JourneyViewModel
import com.example.oplev.data.AppDatabase
import com.example.oplev.data.dataService.CategoryDataService
import com.example.oplev.data.dataService.JourneyDataService
import com.example.oplev.data.dataService.QueueDataService
import com.example.oplev.data.dataService.UserDataService
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import io.grpc.Context

//import com.example.oplev.Model.Journey

class DependencyController() {


    fun initFrontPageViewModel(context: android.content.Context): FrontPageViewModel {
        return FrontPageViewModel()
    }

    fun initCreateJourneyViewModel(context: android.content.Context,application: Application):CreateJourneyViewModel{
        val appDb = AppDatabase.getInstance(context)
        val queueDataService = QueueDataService(appDb)
        val journeyDataService = JourneyDataService(appDb.JourneyDao(),queueDataService)
        val categoryDataService = CategoryDataService(appDb.CategoryDao())
        return CreateJourneyViewModel(journeyDataService, categoryDataService, application)

    }
    fun intiJourneyViewModel(context: android.content.Context, application: Application):JourneyViewModel{
        val appDb = AppDatabase.getInstance(context)
        val queueDataService = QueueDataService(appDb)
        val journeyDataService = JourneyDataService(appDb.JourneyDao(),queueDataService)


        return JourneyViewModel(journeyDataService, application)
    }

    fun initAuthViewModel(context: android.content.Context):AuthViewModel{
        val appDb = AppDatabase.getInstance(context)
        val userDataService = UserDataService(Firebase.firestore,appDb.UserDao())
        return  AuthViewModel(userDataService)
    }
}

