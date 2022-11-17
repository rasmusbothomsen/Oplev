package com.example.oplev.sites.Journy

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import com.example.oplev.Model.Category
import com.example.oplev.Model.Journey
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
                      ExposedDropdownMenu(list = createJourneyViewModel.categories, imageVector = Icons.Filled.Warning)
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

@Composable
fun DatePickerTest(context : Context) {
    val year : Int
    val month : Int
    val day : Int
}

@Composable
fun ExposedDropdownMenu(list : List<Category>, imageVector: ImageVector){
    var expanded by remember { mutableStateOf(false)
    }
    var listOfTitles = listOf<String>("")

    for (category : Category in list){
        listOfTitles = listOf<String>(category.title)
    }

    val list = listOfTitles
    var selectedItem by remember { mutableStateOf("") }
    var textFilledSize by remember { mutableStateOf(Size.Zero) }
    val icon = if (!expanded)
    {
        Icons.Filled.KeyboardArrowUp
    }
    else
    {
        Icons.Filled.KeyboardArrowDown
    }

        Row() {
            Icon(
                imageVector = imageVector,
                contentDescription = "",
                modifier = Modifier.padding(20.dp, 15.dp, 15.dp, 20.dp)
            )

            OutlinedTextField(
                value = selectedItem,
                onValueChange = { selectedItem = it },
                modifier = Modifier
                    .width(280.dp)
                    .padding(0.dp, 0.dp, 0.dp, 20.dp),
                label = { Text(text = "Kategori") },
                trailingIcon = {
                    Icon(icon, "", Modifier.clickable { expanded = !expanded })
                }
            )
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .width(with(LocalDensity.current) { textFilledSize.width.toDp() })
            ) {
                for (title: String in list) {
                    DropdownMenuItem(onClick = {
                        selectedItem = title
                        expanded = false
                    }) {
                        Text(text = title)

                    }
                }

            }
        }
}



@Preview(showBackground = true)
@Composable
fun createJourneyPreview(){
    val journey1 = Journey("e","img_denmark",null,"","Danmark", null)
    val journey2 = Journey("e","img_norway",null,"","Norge",null)
    val journey3 = Journey("e","img_finland",null,"","Finland",null)
    val journey4 = Journey("e","img_turkey",null,"","Tyrkiet",null)
    val journeys = listOf(journey1, journey2, journey3, journey4)

    val seneste = Category("Seneste", journeys)
    val favoritter = Category("Favoritter", journeys)
    val categories = listOf(seneste,favoritter)
    val createJourneyViewModel = CreateJourneyViewModel(categories)
    createJourneyComp(createJourneyViewModel)
    }

