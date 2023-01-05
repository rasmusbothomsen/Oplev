package com.example.oplev.sites


import android.app.Activity
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import androidx.navigation.NavController
import com.example.oplev.data.dto.CategoryDto
import com.example.oplev.Model.Category
import com.example.oplev.Model.Journey
import com.example.oplev.Model.States
import com.example.oplev.Screen
import com.example.oplev.ViewModel.FrontPageViewModel
import com.example.oplev.ViewModel.AuthViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.runBlocking

@Composable
fun ProfileView(authViewModel: AuthViewModel, navController: NavController) {
    val scaffoldstate = rememberScaffoldState()
    val state = authViewModel.state.value
    Scaffold(
        scaffoldState = scaffoldstate,
        topBar = { TopBar("") },
        content = { ProfileContent(authViewModel, navController, state) },
    )
}

@Composable
fun ProfileContent(authViewModel: AuthViewModel, navController: NavController, states: States) {
    //billede
    //Navn (Redigerbar tekst)
    //Skift kode
    //Slet profil
}


