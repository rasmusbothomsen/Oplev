package com.example.oplev.sites.Journy

import android.app.Activity
import android.content.Context
import androidx.compose.foundation.*
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.oplev.Model.Category
import com.example.oplev.R
import com.example.oplev.Screen
import com.example.oplev.ViewModel.CreateJourneyViewModel
import com.example.oplev.data.dto.CategoryDto

@Composable
fun createJourneyComp(createJourneyViewModel: CreateJourneyViewModel, navController: NavController){
    var ID by remember { mutableStateOf(0) }
    var tag by remember { mutableStateOf("") }
    var Image by remember { mutableStateOf("") }
    var CategoryId by remember { mutableStateOf(0) }
    var Date by remember { mutableStateOf("") }
    var Description by remember { mutableStateOf("") }
    var Title by remember { mutableStateOf("") }

    var category by remember { mutableStateOf("") }

    Scaffold(
        topBar = { TopBar(title = "Velkommen" /** + profile.userName **/ )},
        content = {
            //Nedenstående skal i en composable
                  Column(modifier = Modifier
                      .fillMaxSize()
                      .verticalScroll(rememberScrollState())) {
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
                      inputTextfield("Destination",80,imageVector = Icons.Filled.LocationOn, onValueChange = {
                          Title = it
                      },Title)
                     //Nedenstående skal være dropdown
                      ExposedDropdownMenu(list = createJourneyViewModel.getCategories(), imageVector = Icons.Filled.Warning, category) {
                          category = it.title
                      }


                      inputTextfield("Inviter Venner",80, imageVector = Icons.Filled.Warning, onValueChange = {
                              /*TODO*/
                          },"")
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
                      inputTextfield("Beskriv oplevelsen",height = 150,imageVector = Icons.Filled.Menu, onValueChange = {
                          Description = it
                      },Description)
                      Row(modifier = Modifier.padding(60.dp,20.dp,0.dp,80.dp)) {
                          //Nedenstående buttons skal være composables
                          Button(
                              onClick = {
                                        navController.navigate(Screen.FrontPageScreen.route)
                                        },
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
                          val context = LocalContext.current
                          val activity = LocalContext.current as Activity
                          Button(
                              onClick = { createJourneyViewModel.createNewJourney(ID, tag, Image, CategoryId, Date, Description, Title)
                                  navController.navigate(Screen.FrontPageScreen.route)
                                        },
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


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ExposedDropdownMenu(list: List<Category>, imageVector: ImageVector, selectedOption:String, upDateValue: (Category) -> Unit){
    var expanded by remember { mutableStateOf(false) }
    var selectedOptionlocal by remember {
        mutableStateOf(selectedOption)
    }
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
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            }
        ) {
            OutlinedTextField(
                value = selectedOptionlocal,
                readOnly = true,
                onValueChange = {selectedOptionlocal = it},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 0.dp, 0.dp, 20.dp),
                label = { Text(text = "Kategori") },
                trailingIcon = {
                    Icon(icon, "", Modifier.clickable { expanded = !expanded })
                }
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = {
                    expanded = false
                }
            ) {
                list.forEach { selectionOption ->
                    DropdownMenuItem(
                        onClick = {
                            upDateValue.invoke(selectionOption)
                            selectedOptionlocal = selectionOption.title.toString()
                            expanded = false
                        }
                    ) {
                        Text(text = selectionOption.title.toString())
                    }
                }
            }
        }
    }
}



@OptIn(ExperimentalMaterialApi::class)
@Preview(showBackground = true)
@Composable
fun createJourneyPreview(){


    }

