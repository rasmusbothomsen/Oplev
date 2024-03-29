package com.example.oplev.sites.Idea

import android.annotation.SuppressLint
import android.util.EventLogTags
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.oplev.data.dto.JourneyDto
import com.example.oplev.R
import com.example.oplev.Screen
import com.example.oplev.ViewModel.*
import com.example.oplev.sites.TopBar
import com.example.oplev.ui.components.DateandTimePicker
import com.example.oplev.ui.theme.Farvekombi031
import com.example.oplev.ui.theme.Farvekombi032
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import compose.icons.LineAwesomeIcons
import compose.icons.lineawesomeicons.*


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun CreateIdea(createIdeaViewModel: CreateIdeaViewModel, navController: NavController) {

    var titel by remember { mutableStateOf("") }
    var beskrivelse by remember { mutableStateOf("") }
    var link by remember { mutableStateOf("") }
    var image by remember { mutableStateOf("") }
    var folderId by remember { mutableStateOf("") }
    var Date by remember { mutableStateOf("") }
    var dstate = mutableStateOf(false)




    Scaffold(
        topBar = { TopBar(title = "Velkommen {user}", navController) },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                Box(modifier = Modifier.height(120.dp)) {
                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    )
                    {
                        Image(
                            painter = painterResource(id = R.drawable.img_denmark),
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight(),
                            contentDescription = "PlaceholderImage",
                            contentScale = ContentScale.FillBounds
                        )
                    }
                }
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth()
                        .padding(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {


                    OutlinedTextField(
                        value = titel,
                        label = { Text(text = "Idé") },
                        modifier = Modifier
                            .width(300.dp)
                            .align(Alignment.CenterHorizontally),
                        onValueChange = { titel = it },
                        colors = TextFieldDefaults.textFieldColors(
                            textColor = Color.Black,
                            backgroundColor = Color.White,
                            focusedIndicatorColor = Color.Black,
                            unfocusedIndicatorColor = Color.DarkGray,
                            disabledIndicatorColor = Color.DarkGray
                        ),
                        shape = CircleShape,
                        leadingIcon = {
                            Icon(
                                imageVector = LineAwesomeIcons.Lightbulb,
                                contentDescription = "",
                                tint = Farvekombi032
                            )
                        },
                    )
                    val maxChar = 150

                    OutlinedTextField(
                        value = beskrivelse,
                        onValueChange = {
                            if (it.length <= maxChar) beskrivelse = it
                        },
                        label = { Text(text = "Beskrivelse") },
                        colors = TextFieldDefaults.textFieldColors(
                            textColor = Color.Black,
                            backgroundColor = Color.White,
                            focusedIndicatorColor = Color.DarkGray,
                            unfocusedIndicatorColor = Color.DarkGray,
                            disabledIndicatorColor = Color.DarkGray
                        ),
                        shape = RoundedCornerShape(20.dp),
                        modifier = Modifier
                            .width(300.dp)
                            .align(Alignment.CenterHorizontally)
                            .height(200.dp),
                        leadingIcon = {
                            Icon(
                                imageVector = LineAwesomeIcons.PenSolid,
                                contentDescription = "",
                                tint = Farvekombi032,
                                modifier = Modifier.padding(bottom = 135.dp)
                            )
                        }
                    )

                    OutlinedTextField(
                        value = link,
                        label = { Text(text = "Link") },
                        modifier = Modifier
                            .width(300.dp)
                            .align(Alignment.CenterHorizontally),
                        onValueChange = { link = it },
                        colors = TextFieldDefaults.textFieldColors(
                            textColor = Color.Black,
                            backgroundColor = Color.White,
                            focusedIndicatorColor = Color.Black,
                            unfocusedIndicatorColor = Color.DarkGray,
                            disabledIndicatorColor = Color.DarkGray
                        ),
                        shape = CircleShape,
                        leadingIcon = {
                            Icon(
                                imageVector = LineAwesomeIcons.LinkSolid,
                                contentDescription = "",
                                tint = Farvekombi032
                            )
                        },
                    )

                    // INDSÆT CREDIT TIL DATEPICKER
                    val dialogState = rememberMaterialDialogState(false)

                    OutlinedTextField(
                        value = Date,
                        label = { Text(text = "Dato") },
                        onValueChange = {
                            Date = it
                            dialogState.show()
                        }, modifier = Modifier
                            .width(300.dp)
                            .clickable(onClick = { dstate.value = true })
                            .align(Alignment.CenterHorizontally),
                        enabled = false,
                        colors = TextFieldDefaults.textFieldColors(
                            textColor = Color.Black,
                            backgroundColor = Color.White,
                            focusedIndicatorColor = Color.Black,
                            unfocusedIndicatorColor = Color.Black,
                            disabledIndicatorColor = Color.Black,
                            disabledTextColor = Color.DarkGray,
                            disabledLabelColor = Color.Gray
                        ),
                        leadingIcon = {
                            Icon(
                                imageVector = LineAwesomeIcons.Calendar,
                                contentDescription = "",
                                tint = Farvekombi032
                            )
                        },
                        shape = CircleShape
                    )
                    Spacer(modifier = Modifier.width(20.dp))


                    if (dstate.value) {
                        dialogState.show()
                    }

                    MaterialDialog(
                        dialogState = dialogState,
                        buttons = {
                            positiveButton(text = "Ok", onClick = {
                                dstate.value = false
                            })
                            negativeButton(text = "Annuller", onClick = { dstate.value = false })
                        }
                    ) {
                        datepicker { date ->
                            val year = date.year
                            val month = date.month
                            val day = date.dayOfMonth
                            Date = "$day/$month/$year"

                        }
                    }


                    Row(modifier = Modifier.padding(20.dp, 20.dp, 30.dp, 80.dp)) {
                        Button(
                            onClick = { navController.navigate(Screen.FrontPageScreen.route) },
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = Farvekombi031,
                                contentColor = Color.Black
                            ),
                            modifier = Modifier.size(130.dp, 40.dp),
                            shape = RoundedCornerShape(50)
                        ) {
                            Text(
                                text = "Annuller", fontSize = 18.sp
                            )
                        }
                        Spacer(modifier = Modifier.width(40.dp))
                        Button(
                            onClick = {
                                createIdeaViewModel.createNewIdea(
                                    titel,
                                    beskrivelse,
                                    link,
                                    image,
                                    Date
                                )
                                navController.navigate(Screen.FrontPageScreen.route)
                            },
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = Farvekombi032,
                                contentColor = Color.White
                            ),
                            modifier = Modifier.size(130.dp, 40.dp),
                            shape = RoundedCornerShape(50)
                        ) {
                            Text(
                                text = "Gem", fontSize = 18.sp
                            )
                        }
                    }
                }
            }
        }
    )
}


@Composable
fun inputTextfield(label: String, height: Int, imageVector: ImageVector, onValueChange: (String) -> Unit, input: String){
    Row(modifier = Modifier
        .fillMaxWidth()
        .height(height.dp)
        .padding(10.dp, 0.dp, 0.dp, 10.dp)){
        Icon(
            imageVector = imageVector,
            contentDescription = "",
            modifier = Modifier.padding(10.dp,20.dp,15.dp,0.dp))
        OutlinedTextField(
            value = input,
            label = { Text(text = label) },
            modifier = Modifier
                .height(height.dp)
                .fillMaxWidth(),
            onValueChange = onValueChange
        )
    }
}

@Composable
fun inputFieldNoRow(label: String, height: Int, imageVector: ImageVector){
    var input by remember { mutableStateOf("") }
    Icon(
        imageVector = imageVector,
        contentDescription = "",
        modifier = Modifier.padding(10.dp,15.dp,15.dp,20.dp))
    OutlinedTextField(
        value = input,
        label = { Text(text = label) },
        onValueChange = {
            input = it
        }, modifier = Modifier.width(105.dp)
    )
}

@Preview
@Composable
fun createIdeaPreview(){
    //CreateIdea(CreateIdeaViewModel = CreateIdeaViewModel(JourneyDto(null)))
}


