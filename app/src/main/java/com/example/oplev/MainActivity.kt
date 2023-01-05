package com.example.oplev

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.room.Room
import com.example.oplev.data.AppDatabase

//import com.example.oplev.ui.components.NavController

class MainActivity : ComponentActivity() {
    companion object Instance{
        lateinit var database: AppDatabase
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            com.example.oplev.ui.components.NavController()
        }
        val database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "oplev-database"
        ).build()
    }
}

