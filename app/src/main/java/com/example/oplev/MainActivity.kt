package com.example.oplev

import android.app.Application
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.room.Room
import com.example.oplev.data.AppDatabase
import kotlinx.coroutines.runBlocking

//import com.example.oplev.ui.components.NavController

class MainActivity : ComponentActivity() {
    companion object Instance{

        lateinit var context: Context
        lateinit var database: AppDatabase
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        runBlocking{
            context = applicationContext
            database = AppDatabase.getInstance(context)
            AppDatabase.CreateDummyData()
        }
        setContent {
            com.example.oplev.ui.components.NavController(application)

        }

    }
}

