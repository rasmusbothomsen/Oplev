package com.example.oplev

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.room.Room
import com.example.oplev.data.AppDatabase

//import com.example.oplev.ui.components.NavController

class MainActivity : ComponentActivity() {
    companion object {
        lateinit var instance: AppDatabase
        var databaseCreated = false
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        if (!databaseCreated) {
            instance = Room.databaseBuilder(
                applicationContext,
                AppDatabase::class.java,
                "database-testName"
            ).build()
            databaseCreated = true
        }
        super.onCreate(savedInstanceState)
        setContent {
            com.example.oplev.ui.components.NavController()
        }
    }
}

