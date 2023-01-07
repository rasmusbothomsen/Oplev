package com.example.oplev.sites


import android.app.Activity
import android.graphics.drawable.Icon
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import androidx.navigation.NavController
import com.example.oplev.data.dto.CategoryDto
import com.example.oplev.Model.Category
import com.example.oplev.Model.Journey
import com.example.oplev.Model.States
import com.example.oplev.R
import com.example.oplev.Screen
import com.example.oplev.ViewModel.FrontPageViewModel
import com.example.oplev.ViewModel.AuthViewModel
import com.example.oplev.ui.theme.fonts
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
        content = { LoginContent(authViewModel, navController, state) },
    )
}

@Composable
fun LoginContent(authViewModel: AuthViewModel, navController: NavController, states: States) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val backgroundColor = Color(("#ECC5C9").toColorInt())
    val logotextcol = Color(("#004070").toColorInt())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {
        Spacer(modifier = Modifier.height(150.dp))
        Row(modifier = Modifier
            .graphicsLayer {
                translationX = 30f
            }) {
            Image(
                painter = painterResource(id = R.drawable.oplev_logo), contentDescription = "",
                modifier = Modifier
                    .height(102.dp)
                    .width(113.dp)
                    .padding(top = 0.dp)
            )
            Text(
                text = "OPLEV",
                fontSize = 64.sp,
                fontFamily = fonts,
                letterSpacing = 10.sp,
                fontWeight = FontWeight.SemiBold,
                color = logotextcol
            )
        }

        Spacer(modifier = Modifier.height(50.dp))

        Column() {

            OutlinedTextField(
                value = email,
                label = { Text(text = "Email", textAlign = TextAlign.Center) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(70.dp, 0.dp, 70.dp, 0.dp),
                onValueChange = {
                    email = it
                },
                singleLine = true,
                colors = TextFieldDefaults.textFieldColors(
                    textColor = Color.Black,
                    backgroundColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
                shape = CircleShape
            )

            Spacer(modifier = Modifier.height(10.dp))

            var passwordVisible by rememberSaveable { mutableStateOf(false) }

            OutlinedTextField(
                value = password,
                label = { Text(text = "Password", textAlign = TextAlign.Center) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(70.dp, 0.dp, 70.dp, 0.dp),
                onValueChange = {
                    password = it
                },
                singleLine = true,
                colors = TextFieldDefaults.textFieldColors(
                    textColor = Color.Black,
                    backgroundColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
                shape = CircleShape,
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                trailingIcon = {
                    val image = if (passwordVisible)
                        Icons.Filled.Visibility
                    else Icons.Filled.VisibilityOff
                    val description = if (passwordVisible) "Hide password" else "Show password"
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(imageVector = image, description)
                    }
                }
            )
            TextButton(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .padding(195.dp, 0.dp, 70.dp, 0.dp)
            ) {
                Text(
                    text = "Glemt kodeord",
                    color = Color.Black
                )
            }

        }

        val context = LocalContext.current
        val activity = LocalContext.current as Activity

        Spacer(modifier = Modifier.height(50.dp))

        Button(
            onClick = {
                runBlocking {
                    authViewModel.signIn(email, password, context, activity)
                }
                if (FirebaseAuth.getInstance().currentUser != null) {
                    navController.navigate(Screen.FrontPageScreen.route)
                }
            }, shape = CircleShape,
            colors = ButtonDefaults.buttonColors(backgroundColor = logotextcol),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .padding(90.dp,0.dp,90.dp,0.dp)
        ) {
            Text(text = "LOG IND", fontSize = 16.sp, color = Color.White)
        }

        TextButton(onClick = { navController.navigate(Screen.SignUpScreen.route) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(90.dp,15.dp,90.dp,0.dp)) {
            Text(text = "OPRET BRUGER", fontSize = 16.sp, color = Color.Black)
        }

    }
}


