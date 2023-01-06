package com.example.oplev.sites


import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import androidx.navigation.NavController
import com.example.oplev.Model.States
import com.example.oplev.R
import com.example.oplev.Screen
import com.example.oplev.ViewModel.AuthViewModel

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
    Column(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)) {
                //Halvcirkel
                Image(
                    painter = painterResource(id = R.drawable.design_uden_navn__9_),
                    contentDescription = "halfcircle",
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.TopCenter)
                )
                Image(painter = painterResource(id = R.drawable.design_uden_navn__8_),
                    contentDescription = "Background pic",
                    modifier = Modifier
                        .fillMaxWidth()
                        .graphicsLayer {
                            translationX = 0f
                            translationY = 160f
                        })
        }

        //SKAL HENTE BRUGERNAVN
        val fullname = remember { mutableStateOf("Berfin Flora Turan") }
        var nameEditable = states.nameEditable

        TextField(
            // below line is used to get
            // value of text field,
            value = fullname.value,

            // below line is used to get value in text field
            // on value change in text field.
            onValueChange = { fullname.value = it },

            // below line is used to add placeholder
            // for our text field.
            placeholder = { Text(text = "Berfin Flora Turan") },

            // modifier is use to add padding
            // to our text field, and a circular border
            modifier = Modifier
                .padding(all = 16.dp)
                .fillMaxWidth()
                .border(1.dp, Color.LightGray, CircleShape),

            shape = CircleShape,

            colors = TextFieldDefaults.textFieldColors(
                textColor = Color.Black,
                disabledTextColor = Color.Gray,
                backgroundColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
        trailingIcon = {
            IconButton(onClick = { authViewModel.nameStateChange() }) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = "" )
            }
        },
        enabled = nameEditable)

        //SKAL HENTE MAIL FRA DB
        val phoneNum = remember { mutableStateOf("bfloraturan@gmail.com") }
        var phoneNumEditable = states.phoneNumEditable

        TextField(
            // below line is used to get
            // value of text field,
            value = phoneNum.value,

            // below line is used to get value in text field
            // on value change in text field.
            onValueChange = { phoneNum.value = it },

            // below line is used to add placeholder
            // for our text field.
            placeholder = { Text(text = "+45 12 34 56 78") },

            // modifier is use to add padding
            // to our text field, and a circular border
            modifier = Modifier
                .padding(all = 16.dp)
                .fillMaxWidth()
                .border(1.dp, Color.LightGray, CircleShape),

            shape = CircleShape,

            colors = TextFieldDefaults.textFieldColors(
                textColor = Color.Black,
                disabledTextColor = Color.Gray,
                backgroundColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            trailingIcon = {
                IconButton(onClick = { authViewModel.PhoneStateChange() }) {
                    Icon(imageVector = Icons.Default.Edit, contentDescription = "" )
                }
            },
            enabled = phoneNumEditable)

        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 16.dp, 16.dp, 0.dp)) {

            TextButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Default.Lock, contentDescription = "", tint = Color.Black )
                Spacer(modifier = Modifier.width(20.dp))
                Text(text = "Skift adgangskode", modifier = Modifier.padding(top=2.5.dp), color = Color.Black)
                Spacer(modifier = Modifier.width(140.dp))
                Icon(imageVector = Icons.Default.KeyboardArrowRight, contentDescription = "",
                    modifier = Modifier.padding(bottom=15.dp), tint = Color.Black)
            }

        }
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 16.dp, 16.dp, 0.dp)) {
            TextButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Default.MailOutline, contentDescription = "", tint = Color.Black )
                Spacer(modifier = Modifier.width(20.dp))
                Text(text = "Skift mail", modifier = Modifier.padding(top=2.5.dp), color = Color.Black)
                Spacer(modifier = Modifier.width(205.dp))
                Icon(imageVector = Icons.Default.KeyboardArrowRight, contentDescription = "",
                    modifier = Modifier.padding(bottom=15.dp), tint = Color.Black)
            }
        }
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 16.dp, 16.dp, 0.dp)) {
            TextButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Default.Close, contentDescription = "", tint = Color.Black )
                Spacer(modifier = Modifier.width(20.dp))
                Text(text = "Slet konto", modifier = Modifier.padding(top=2.5.dp), color = Color.Black)
                Spacer(modifier = Modifier.width(200.dp))
                Icon(imageVector = Icons.Default.KeyboardArrowRight, contentDescription = "",
                    modifier = Modifier.padding(bottom=15.dp), tint = Color.Black)
            }
        }
        Spacer(modifier = Modifier.height(20.dp))

        val hexCode = "#79B56C"
        Button(onClick = {
            /* TODO */
            navController.navigate(Screen.FrontPageScreen.route)
        }, modifier = Modifier.align(Alignment.CenterHorizontally),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(hexCode.toColorInt()), contentColor = Color.White),
            shape = CircleShape
        ) {
            Text(text = "Gem ændringer")
        }

    }


}
