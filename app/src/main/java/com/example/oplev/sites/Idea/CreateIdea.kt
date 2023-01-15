package com.example.oplev.sites.Idea

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Warning
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
import compose.icons.LineAwesomeIcons
import compose.icons.lineawesomeicons.Calendar
import compose.icons.lineawesomeicons.Comment
import compose.icons.lineawesomeicons.Lightbulb
import compose.icons.lineawesomeicons.LinkSolid


@Composable
fun CreateIdea(createIdeaViewModel: CreateIdeaViewModel, navController: NavController) {

    var titel by remember { mutableStateOf("") }
    var beskrivelse by remember { mutableStateOf("") }
    var link by remember { mutableStateOf("") }
    var image by remember { mutableStateOf("") }
    var folderId by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }



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


                    inputTextfield(
                        label = "Titel",
                        height = 80,
                        imageVector = LineAwesomeIcons.Lightbulb,
                        onValueChange = { titel = it },
                        input = titel
                    )
                    inputTextfield(
                        label = "Beskriv ideen",
                        height = 200,
                        imageVector = LineAwesomeIcons.Comment,
                        onValueChange = { beskrivelse = it },
                        input = beskrivelse
                    )
                    inputTextfield(
                        label = "Indsæt link",
                        height = 80,
                        imageVector = LineAwesomeIcons.LinkSolid,
                        onValueChange = { link = it },
                        input = link
                    )
                    inputTextfield(
                        label = "Indsæt dato",
                        height = 80,
                        imageVector = LineAwesomeIcons.Calendar,
                        onValueChange = { date = it },
                        input = date
                    )


                    Row(modifier = Modifier.padding(20.dp, 20.dp, 30.dp, 80.dp)) {
                        Button(
                            onClick = { navController.navigate(Screen.FrontPageScreen.route) },
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = Color.Transparent,
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
                                    date
                                )
                                navController.navigate(Screen.FrontPageScreen.route)
                            },
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = Color.Green,
                                contentColor = Color.Black
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


