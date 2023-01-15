package com.example.oplev

import CreateJourneyViewModel
import JourneyViewModel
import android.app.Application
import com.example.oplev.Model.Journey
import com.example.oplev.ViewModel.*
import com.example.oplev.data.AppDatabase
import com.example.oplev.data.dataService.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import io.grpc.Context

//import com.example.oplev.Model.Journey

class DependencyController() {


    fun initFrontPageViewModel(context: android.content.Context,application: Application): FrontPageViewModel {
        val appDb = AppDatabase.getInstance(context)
        val queueDataService = QueueDataService(appDb)
        val categoryDataService = CategoryDataService(appDb.CategoryDao(), queueDataService)
        val userDataService = UserDataService(Firebase.firestore,appDb.UserDao())
        val journeyDataService = JourneyDataService(appDb.JourneyDao(), queueDataService)
        return FrontPageViewModel(application, categoryDataService, journeyDataService,userDataService)
    }

    fun initCreateJourneyViewModel(context: android.content.Context,application: Application, journeyId: String?):CreateJourneyViewModel{
        val appDb = AppDatabase.getInstance(context)
        val queueDataService = QueueDataService(appDb)
        val journeyDataService = JourneyDataService(appDb.JourneyDao(),queueDataService)
        val categoryDataService = CategoryDataService(appDb.CategoryDao(), queueDataService)
        val folderDataService = FolderDataService(appDb.FolderDao(),queueDataService)
        val userDataService = UserDataService(Firebase.firestore,appDb.UserDao())
        return CreateJourneyViewModel(journeyDataService, categoryDataService, userDataService, application, folderDataService,journeyId)


    }
    fun intiJourneyViewModel(context: android.content.Context, application: Application,journeyId: String):JourneyViewModel{
        val appDb = AppDatabase.getInstance(context)
        val queueDataService = QueueDataService(appDb)
        val folderDataService = FolderDataService(appDb.FolderDao(),queueDataService)
        val journeyDataService = JourneyDataService(appDb.JourneyDao(),queueDataService)
        return JourneyViewModel(journeyDataService, folderDataService ,application,journeyId)
    }

    fun initAuthViewModel(context: android.content.Context, application: Application):AuthViewModel{
        val appDb = AppDatabase.getInstance(context)
        val userDataService = UserDataService(Firebase.firestore,appDb.UserDao())
        val queueDataService = QueueDataService(appDb)
        val categoryDataService = CategoryDataService(appDb.CategoryDao(), queueDataService )
        return  AuthViewModel(userDataService, application, categoryDataService,queueDataService)
    }

    fun initOnboardingViewModel(context: android.content.Context, application: Application): OnboadringViewModel{
        val appDb = AppDatabase.getInstance(context)
        val userDataService = UserDataService(Firebase.firestore,appDb.UserDao())

        return OnboadringViewModel(userDataService, application)
    }

    fun initIdeaViewModel(context: android.content.Context, application: Application, ideaId: String):IdeaViewModel{
        val appDb = AppDatabase.getInstance(context)
        val queueDataService = QueueDataService(appDb)
        val ideaDataService = IdeaDataService(appDb.IdeaDao(),queueDataService)
        return IdeaViewModel(ideaDataService, application, ideaId)
    }

    fun initCreateIdeaViewModel(context: android.content.Context, application: Application,folderId:String, ideaId: String?):CreateIdeaViewModel{
        val appDb = AppDatabase.getInstance(context)
        val queueDataService = QueueDataService(appDb)
        val ideaDataService = IdeaDataService(appDb.IdeaDao(),queueDataService)
        return CreateIdeaViewModel(ideaDataService, application,folderId, ideaId)

    }



}

