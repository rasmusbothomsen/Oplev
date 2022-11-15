package com.example.oplev.sites

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.example.oplev.R
import com.example.oplev.Screen
import com.example.oplev.ui.components.Button
import com.example.oplev.ui.components.Fab

class SignUp{
}

@Composable
fun SignUpScreen(navController : NavController) {
   androidx.compose.material.Button(onClick = { 
      navController.navigate(Screen.FrontPageScreen.route)
   }) {
      Text(text = "DU ER PÅ SIGN UP SIDEN")
   }
}
