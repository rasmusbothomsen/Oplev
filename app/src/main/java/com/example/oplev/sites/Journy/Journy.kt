package com.example.oplev.sites.Journy

import androidx.compose.foundation.Image
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.oplev.Model.Journey
import com.example.oplev.R
import com.example.oplev.ViewModel.JourneyViewModel
import com.example.oplev.ui.components.Button

@Composable
fun JournyScreen(journyViewModel: JourneyViewModel, modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ){
        Column(modifier = Modifier.padding(32.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Image(painter = painterResource(id = R.drawable.img_norway), contentDescription = null)
            Text(journyViewModel.journeyImage())
        }
    }
}
@Preview(showBackground = true)
@Composable
fun JourneyPreview(){
    val journyviewModel = JourneyViewModel(Journey("e","Testing",null,"","",null))
    JournyScreen(journyviewModel)
}