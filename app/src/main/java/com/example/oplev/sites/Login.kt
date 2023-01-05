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
fun LoginView(authViewModel: AuthViewModel, navController: NavController) {
    val scaffoldstate = rememberScaffoldState()
    val state = authViewModel.state.value
    Scaffold(
        scaffoldState = scaffoldstate,
        topBar = { TopBar("") },
        content = { LoginContent(authViewModel, navController, state) },
    )
}

@Composable
fun LoginContent(authViewModel: AuthViewModel, navController: NavController, states: States) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize()) {

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = email,
            label = { Text(text = "Email", textAlign = TextAlign.Center) },
            modifier = Modifier
                .width(130.dp),
            onValueChange = {
                email = it
            },
            singleLine = true
        )

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = password,
            label = { Text(text = "Password", textAlign = TextAlign.Center) },
            modifier = Modifier
                .width(130.dp),
            onValueChange = {
                password = it
            },
            singleLine = true
        )

        val context = LocalContext.current
        val activity = LocalContext.current as Activity

        Button(
            onClick = {
                runBlocking {
                    authViewModel.signIn(email, password, context, activity)
                }
                if (FirebaseAuth.getInstance().currentUser != null){
                    navController.navigate(Screen.FrontPageScreen.route)
                }
            }
        ) {
            Text(text = "Login")
        }

        TextButton(onClick = { navController.navigate(Screen.SignUpScreen.route) }) {
            Text(text = "Create User")
        }

    }
}


