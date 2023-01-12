package com.example.oplev.sites


import android.app.Activity
import android.text.Layout.Alignment
import android.view.Gravity
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking

@Composable
fun CreateUserView(authViewModel: AuthViewModel, navController: NavController) {
    val scaffoldstate = rememberScaffoldState()
    val state = authViewModel.state.value
    Scaffold(
        scaffoldState = scaffoldstate,
        content = { CreateUserContent(authViewModel, navController, state) },
    )
}

@Composable
fun CreateUserContent(authViewModel: AuthViewModel, navController: NavController, states: States) {
    var fullname by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confPassword by remember { mutableStateOf("") }
    val logotextcol = Color(("#004070").toColorInt())
    val buttonCol = Color(("#ECC5C9").toColorInt())

    Column(modifier = Modifier
        .fillMaxSize()
        .background(color = Color.White)
        .verticalScroll(rememberScrollState())) {

        Box(modifier = Modifier
            .fillMaxWidth()
            .height(160.dp)) {

            Image(
                    painter = painterResource(id = R.drawable.top_blob),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(androidx.compose.ui.Alignment.TopEnd)
            )

            Box(modifier = Modifier
                    .graphicsLayer {
                        translationX = 0f
                        translationY = 300f
                    }){
            Button(
                    onClick = {

                    }, shape = CircleShape,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .padding(60.dp, 0.dp, 60.dp, 0.dp),
                    colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color.White,
                            contentColor = Color.DarkGray
                    )
            ) {
                Image(
                        painter = painterResource(id = R.drawable.google_icon),
                        contentDescription = "",
                        modifier = Modifier.height(25.dp)
                )
                Text(text = "Log ind med Google", modifier = Modifier.padding(6.dp))
            }
        }
        }
        Spacer(modifier = Modifier.height(10.dp))
        Button(
                onClick = {

                }, shape = CircleShape,
                modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .padding(60.dp, 0.dp, 60.dp, 0.dp),
                colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.White,
                        contentColor = Color.DarkGray
                )
        ) {
            Image(
                    painter = painterResource(id = R.drawable.facebook_icon),
                    contentDescription = "",
                    modifier = Modifier.height(25.dp)
            )
            Text(text = "Log ind med Facebook", modifier = Modifier.padding(6.dp))
        }


        Spacer(modifier = Modifier.height(25.dp))

        OutlinedTextField(
                value = fullname,
                label = { Text(text = "Fulde navn", textAlign = TextAlign.Center) },
                modifier = Modifier
                        .fillMaxWidth()
                        .padding(70.dp, 0.dp, 70.dp, 0.dp),
                onValueChange = {
                    fullname = it
                },
                singleLine = true,
                colors = TextFieldDefaults.textFieldColors(
                        textColor = Color.Black,
                        backgroundColor = Color.White,
                        focusedIndicatorColor = Color.DarkGray,
                        unfocusedIndicatorColor = Color.DarkGray,
                        disabledIndicatorColor = Color.Transparent
                ),
                shape = CircleShape
        )

        Spacer(modifier = Modifier.height(5.dp))

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
                        focusedIndicatorColor = Color.DarkGray,
                        unfocusedIndicatorColor = Color.DarkGray,
                        disabledIndicatorColor = Color.Transparent
                ),
                shape = CircleShape
        )

        Spacer(modifier = Modifier.height(5.dp))

        var passwordVisible by rememberSaveable { mutableStateOf(false) }

        OutlinedTextField(
                value = password,
                label = { Text(text = "Adgangskode", textAlign = TextAlign.Center) },
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
                        focusedIndicatorColor = Color.DarkGray,
                        unfocusedIndicatorColor = Color.DarkGray,
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

        Spacer(modifier = Modifier.height(5.dp))

        var confPasswordVisible by rememberSaveable { mutableStateOf(false) }

        OutlinedTextField(
                value = confPassword,
                label = { Text(text = "Gentag adgangskode", textAlign = TextAlign.Center) },
                modifier = Modifier
                        .fillMaxWidth()
                        .padding(70.dp, 0.dp, 70.dp, 0.dp),
                onValueChange = {
                    confPassword = it
                },
                singleLine = true,
                colors = TextFieldDefaults.textFieldColors(
                        textColor = Color.Black,
                        backgroundColor = Color.White,
                        focusedIndicatorColor = Color.DarkGray,
                        unfocusedIndicatorColor = Color.DarkGray,
                        disabledIndicatorColor = Color.Transparent,
                ),
                shape = CircleShape,
                visualTransformation = if (confPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                trailingIcon = {
                    val image = if (confPasswordVisible)
                        Icons.Filled.Visibility
                    else Icons.Filled.VisibilityOff
                    val description = if (confPasswordVisible) "Hide password" else "Show password"
                    IconButton(onClick = { confPasswordVisible = !confPasswordVisible }) {
                        Icon(imageVector = image, description)
                    }
                }
        )

        Spacer(modifier = Modifier.height(25.dp))

        val context = LocalContext.current
        val activity = LocalContext.current as Activity
        Button(
                onClick = {
                    if (password.equals(confPassword)){
                    runBlocking {
                        authViewModel.createNewUser(fullname, email, password, context, activity)
                    }
                    if (FirebaseAuth.getInstance().currentUser != null) {
                        navController.navigate(Screen.FrontPageScreen.route)
                    }
                    } else {
                        val toast = Toast.makeText(context, "Adgangskoderne er ikke ens. Prøv igen.", Toast.LENGTH_LONG)
                        toast.show()
                    }

                }, shape = CircleShape,
                colors = ButtonDefaults.buttonColors(backgroundColor = logotextcol),
                modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .padding(90.dp, 0.dp, 90.dp, 0.dp)
        ) {
            Text(text = "OPRET BRUGER", fontSize = 16.sp, color = Color.White)
        }

        Box(modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)) {

            TextButton(
                    onClick = {
                        navController.navigate(Screen.LoginScreen.route)
                    },
                    modifier = Modifier
                            .fillMaxWidth()
                            .padding(90.dp, 15.dp, 90.dp, 0.dp)
            ) {
                Text(text = "LOG IND", fontSize = 16.sp, color = Color.Black)
            }

            Image(
                    painter = painterResource(id = R.drawable.bottom_blob),
                    contentDescription = null,
                    modifier = Modifier
                            .fillMaxWidth()
                            .align(androidx.compose.ui.Alignment.BottomStart)
            )
        }

    }
}

@Preview
@Composable
fun Preview() {
    var fullname by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confPassword by remember { mutableStateOf("") }
    val logotextcol = Color(("#004070").toColorInt())

    Column(modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)) {

        Box(modifier = Modifier
                .fillMaxWidth()
                .height(170.dp)) {
            Image(
                    painter = painterResource(id = R.drawable.top_blob),
                    contentDescription = null,
                    modifier = Modifier
                            .fillMaxWidth()
                            .align(androidx.compose.ui.Alignment.TopEnd)
            )
        }

        Button(
                onClick = {

                }, shape = CircleShape,
                modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .padding(60.dp, 0.dp, 60.dp, 0.dp),
                colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.White,
                        contentColor = Color.DarkGray
                )
        ) {
            Image(
                    painter = painterResource(id = R.drawable.google_icon),
                    contentDescription = "",
                    modifier = Modifier.fillMaxHeight()
            )
            Text(text = "Log ind med Google", modifier = Modifier.padding(6.dp))
        }
        Spacer(modifier = Modifier.height(10.dp))
        Button(
                onClick = {

                }, shape = CircleShape,
                modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .padding(60.dp, 0.dp, 60.dp, 0.dp),
                colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.White,
                        contentColor = Color.DarkGray
                )
        ) {
            Image(
                    painter = painterResource(id = R.drawable.facebook_icon),
                    contentDescription = "",
                    modifier = Modifier.height(25.dp)
            )
            Text(text = "Log ind med Facebook", modifier = Modifier.padding(6.dp))
        }

        Spacer(modifier = Modifier.height(25.dp))

        OutlinedTextField(
                value = fullname,
                label = { Text(text = "Fulde navn", textAlign = TextAlign.Center) },
                modifier = Modifier
                        .fillMaxWidth()
                        .padding(70.dp, 0.dp, 70.dp, 0.dp),
                onValueChange = {
                    fullname = it
                },
                singleLine = true,
                colors = TextFieldDefaults.textFieldColors(
                        textColor = Color.Black,
                        backgroundColor = Color.White,
                        focusedIndicatorColor = Color.DarkGray,
                        unfocusedIndicatorColor = Color.DarkGray,
                        disabledIndicatorColor = Color.Transparent
                ),
                shape = CircleShape
        )

        Spacer(modifier = Modifier.height(5.dp))

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
                        focusedIndicatorColor = Color.DarkGray,
                        unfocusedIndicatorColor = Color.DarkGray,
                        disabledIndicatorColor = Color.Transparent
                ),
                shape = CircleShape
        )

        Spacer(modifier = Modifier.height(5.dp))

        var passwordVisible by rememberSaveable { mutableStateOf(false) }

        OutlinedTextField(
                value = password,
                label = { Text(text = "Adgangskode", textAlign = TextAlign.Center) },
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
                        focusedIndicatorColor = Color.DarkGray,
                        unfocusedIndicatorColor = Color.DarkGray,
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

        Spacer(modifier = Modifier.height(5.dp))

        var confPasswordVisible by rememberSaveable { mutableStateOf(false) }

        OutlinedTextField(
                value = confPassword,
                label = { Text(text = "Gentag adgangskode", textAlign = TextAlign.Center) },
                modifier = Modifier
                        .fillMaxWidth()
                        .padding(70.dp, 0.dp, 70.dp, 0.dp),
                onValueChange = {
                    confPassword = it
                },
                singleLine = true,
                colors = TextFieldDefaults.textFieldColors(
                        textColor = Color.Black,
                        backgroundColor = Color.White,
                        focusedIndicatorColor = Color.DarkGray,
                        unfocusedIndicatorColor = Color.DarkGray,
                        disabledIndicatorColor = Color.Transparent,
                ),
                shape = CircleShape,
                visualTransformation = if (confPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                trailingIcon = {
                    val image = if (confPasswordVisible)
                        Icons.Filled.Visibility
                    else Icons.Filled.VisibilityOff
                    val description = if (confPasswordVisible) "Hide password" else "Show password"
                    IconButton(onClick = { confPasswordVisible = !confPasswordVisible }) {
                        Icon(imageVector = image, description)
                    }
                }
        )

        Spacer(modifier = Modifier.height(25.dp))

        Button(
                onClick = {

                }, shape = CircleShape,
                colors = ButtonDefaults.buttonColors(backgroundColor = logotextcol),
                modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .padding(90.dp, 0.dp, 90.dp, 0.dp)
        ) {
            Text(text = "OPRET BRUGER", fontSize = 16.sp, color = Color.White)
        }

        Box(modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)) {

        TextButton(
                onClick = {

                },
                modifier = Modifier
                        .fillMaxWidth()
                        .padding(90.dp, 15.dp, 90.dp, 0.dp)
        ) {
            Text(text = "LOG IND", fontSize = 16.sp, color = Color.Black)
        }

            Image(
                    painter = painterResource(id = R.drawable.bottom_blob),
                    contentDescription = null,
                    modifier = Modifier
                            .fillMaxWidth()
                            .align(androidx.compose.ui.Alignment.BottomStart)
            )
        }

    }
}


