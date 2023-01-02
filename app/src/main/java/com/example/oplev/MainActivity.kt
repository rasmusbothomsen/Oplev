package com.example.oplev

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory.Companion.instance
import androidx.navigation.NavController
import androidx.room.Room
import com.example.oplev.Data.AppDatabase
import com.example.oplev.Model.testRoom
import com.example.oplev.ui.components.NavController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

//import com.example.oplev.ui.components.NavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val appDatabase = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "database-testName"
        ).build()
        super.onCreate(savedInstanceState)
        setContent {
            com.example.oplev.ui.components.NavController()
        }
        }
    }

