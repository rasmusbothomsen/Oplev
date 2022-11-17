package com.example.oplev.sites.Journy

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.oplev.R
import com.example.oplev.ViewModel.CreateJourneyViewModel

@Composable
fun createJourneyComp(createJourneyViewModel: CreateJourneyViewModel, modifier: Modifier = Modifier){
    Scaffold(
        topBar = { TopBar(title = "Velkommen {USER}")},
        content = {
                  Column(modifier = Modifier.fillMaxSize()) {
                      Button(onClick = { /*TODO*/ }) {
                          Image(painter = painterResource(id = R.drawable.img_denmark), modifier = Modifier
                              .fillMaxWidth()
                              .height(150.dp) ,contentDescription = "PlaceholderImage")
                      }
                      inputTextfield(valueText = "vælg Destination", 20)
                      inputTextfield(valueText = "vælg Kategori", 20)
                      inputTextfield(valueText = "Inviter venner", 20)
                      Spacer(modifier = Modifier.size(50.dp))
                      inputTextfield(valueText = "Beskriv oplevelsen", height = 100)
                      
                  }
        },
        bottomBar = { BottomBar()}
    )
}

@Composable
fun inputTextfield(valueText: String, height: Int){
    var input by remember { mutableStateOf(valueText) }
    Row(modifier = Modifier
        .fillMaxWidth()
        .height(height.dp)
        .padding(10.dp)){
        Image(painter = painterResource(id = R.drawable.img_india), modifier = Modifier
            .size(10.dp)
            .padding(10.dp), contentDescription = "Icon" )
        TextField(
            value = input,
            onValueChange = {input = it},
            modifier = Modifier.fillMaxWidth()
        )
    }
}



@Preview(showBackground = true)
@Composable
fun createJourneyPreview(){
    createJourneyComp(createJourneyViewModel = CreateJourneyViewModel())
    }

