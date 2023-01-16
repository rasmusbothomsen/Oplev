package com.example.oplev

import android.app.Application
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.internal.view.SupportMenuItem
import androidx.room.Room
import com.example.oplev.data.AppDatabase
import com.example.oplev.data.dataService.UserDataService
import com.example.oplev.ui.components.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.runBlocking

//import com.example.oplev.ui.components.NavController

class MainActivity : ComponentActivity() {
    companion object Instance{

        lateinit var context: Context
        lateinit var database: AppDatabase
    }

    private lateinit var auth: FirebaseAuth
    var startPage = Screen.LoginScreen.route

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        actionBar?.hide()
        auth = Firebase.auth
        runBlocking{
            context = applicationContext
            database = AppDatabase.getInstance(context)
            // AppDatabase.CreateDummyData()
        }
        setContent {

            com.example.oplev.ui.components.NavController(application, startPage)

        }

    }

    public override fun onStart() {
        super.onStart()

        // Check if user is signed in (non-null) and update UI accordingly.

            val currentUser = auth.currentUser
            if(currentUser != null){
                val user = database.UserDao().getUserFromId(currentUser.uid.toString())
                if(!database.UserDao().getOBStatus(user.userId)) {
                    startPage = Screen.Onboarding1.route
                }
                else {
                    startPage = Screen.FrontPageScreen.route
                }
            }
        }
    }

