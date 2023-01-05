package com.example.oplev

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.room.Room
import com.example.oplev.data.roomDao.BaseDao

//import com.example.oplev.ui.components.NavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            com.example.oplev.ui.components.NavController()
        }
    }
}

