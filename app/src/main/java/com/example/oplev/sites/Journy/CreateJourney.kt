package com.example.oplev.sites.Journy

import android.app.DatePickerDialog
import android.graphics.Paint
import android.widget.DatePicker
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
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
import com.example.oplev.R
import com.example.oplev.ViewModel.CreateJourneyViewModel

@Composable
fun createJourneyComp(createJourneyViewModel: CreateJourneyViewModel, modifier: Modifier = Modifier){
    Scaffold(
        topBar = { TopBar(title = "Velkommen {USER}")},
        content = {
            //Nedenstående skal i en composable
                  Column(modifier = Modifier.fillMaxSize()) {
                      Box(modifier = Modifier.height(120.dp)){
                          Column(
                              modifier = Modifier
                                  .fillMaxHeight()
                                  .fillMaxWidth()
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
                          IconButton(onClick = { /*TODO*/ },  modifier = Modifier
                              .align(Alignment.BottomEnd)) {
                              Icon(imageVector = Icons.Default.AddCircle, contentDescription ="",
                                  tint=Color.White
                              )
                          }
                          }
                      inputTextfield("Destination",80,imageVector = Icons.Filled.LocationOn)
                     //Nedenstående skal være dropdown
                      inputTextfield("Kategori", 80, imageVector = Icons.Filled.Warning)
                      inputTextfield("Inviter Venner",80, imageVector = Icons.Filled.Warning)
                      //Nedenstående rows skal i en composable
                      Row(modifier = Modifier
                          .fillMaxWidth()
                          .height(80.dp)
                          .padding(10.dp)) {
                          /* Skal være en datepicker*/ inputFieldNoRow("Fra Dato",80, imageVector = Icons.Filled.Warning)
                          Spacer(modifier = Modifier.width(20.dp))
                          /* Skal være en "time picker"*/ inputFieldNoRow("Fra kl.",80, imageVector = Icons.Filled.Warning)
                      }
                      Row(modifier = Modifier
                          .fillMaxWidth()
                          .height(80.dp)
                          .padding(10.dp)) {
                          /* Skal være en datepicker*/ inputFieldNoRow("Til Dato",80, imageVector = Icons.Filled.Warning)
                          Spacer(modifier = Modifier.width(20.dp))
                          /* Skal være en "time picker"*/ inputFieldNoRow("Til kl.",80, imageVector = Icons.Filled.Warning)
                      }
                      inputTextfield("Beskriv oplevelsen",height = 100,imageVector = Icons.Filled.Menu)
                      Row(modifier = Modifier.padding(30.dp,20.dp,0.dp,0.dp)) {
                          //Nedenstående buttons skal være composables
                          Button(
                              onClick = { /*TODO*/ },
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
                          Spacer(modifier = Modifier.width(60.dp))
                          Button(
                              onClick = { /*TODO*/ },
                              colors = ButtonDefaults.buttonColors(
                                  backgroundColor = Color.Blue,
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
        },
        bottomBar = { BottomBar()}
    )
}

@Composable
fun inputTextfield(label: String, height: Int, imageVector: ImageVector){
    var input by remember { mutableStateOf("") }
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
            onValueChange = {
                input = it
            }
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



@Preview(showBackground = true)
@Composable
fun createJourneyPreview(){
    createJourneyComp(createJourneyViewModel = CreateJourneyViewModel())
    }

