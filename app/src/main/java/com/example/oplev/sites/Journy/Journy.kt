package com.example.oplev.sites.Journy

import android.media.Image
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.oplev.Model.Journey
import com.example.oplev.R
import com.example.oplev.sites.*

import com.example.oplev.ViewModel.JourneyViewModel


@Composable
fun JourneyScreen(journeyViewModel: JourneyViewModel, modifier: Modifier = Modifier) {

    Scaffold(
        topBar = {TopBar(title = "Velkommen {User}")},
        content = {
                imageAndText(text = journeyViewModel.journeyTitle(), image = null, journeyViewModel = journeyViewModel)
                /* HER SKAL LAVES MATRIX AF ROW/COLUMN TIL IDEAS*/

                  },
        bottomBar = { BottomBar()} )
}

@Composable
fun imageAndText(
    text: String,
    image: Image?,
    journeyViewModel: JourneyViewModel){
    Column(modifier = Modifier.height(200.dp).fillMaxWidth().verticalScroll(rememberScrollState()))
    {
        Spacer(modifier = Modifier.padding(5.dp))
        Row(modifier = Modifier.height(150.dp)){
            Image(
                painter = painterResource(id = R.drawable.img_denmark),
                contentDescription = "Placerholder image",
                modifier = Modifier.fillMaxHeight().fillMaxWidth()
            )
        }
        Row(modifier = Modifier.height(50.dp)){
            Text(text = journeyViewModel.journeyTitle(), fontSize = 17.sp, textAlign = TextAlign.Center)
        }
    }
}

@Composable
fun folders(journeyViewModel: JourneyViewModel){

}

@Composable
fun ideas(journeyViewModel: JourneyViewModel){

}


@Composable
fun TopBar(title: String) {
    TopAppBar( modifier = Modifier.height(65.dp),
        title = { Text(title, textAlign = TextAlign.Center) },

        navigationIcon = {
            IconButton(onClick = {
                TODO()
            })
            {
                Icon(
                    painter = painterResource(id = R.drawable.oplev72dpi),
                    contentDescription = ""
                )


            }
        },


        backgroundColor = Color.LightGray
    )
}

@Composable
fun BottomBar(){
    BottomAppBar(modifier = Modifier.height(65.dp), cutoutShape = CircleShape,) {
        BottomNavigation() {
            BottomNavigationItem(
                icon = { Icon(imageVector = Icons.Default.Menu, "") },
                label = { Text(text = "Menu") },
                selected = false,
                onClick = {})
            BottomNavigationItem(
                icon = { Icon(imageVector = Icons.Default.Search, "") },
                label = { Text(text = "Søg") },
                selected = false,
                onClick = {})
        }
    }
}

@Preview(showBackground = true)
@Composable
fun JourneyPreview(){
    var testJourney = Journey(tag = "test", image = null, date = null, description = "This is a test", title = "Danmark", folder = null, ideas = null)
    JourneyScreen(journeyViewModel = JourneyViewModel(testJourney))
}