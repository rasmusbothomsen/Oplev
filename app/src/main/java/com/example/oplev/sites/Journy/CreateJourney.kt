package com.example.oplev.sites.Journy

import android.app.Activity
import android.app.LauncherActivity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.icu.text.CaseMap
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Alignment
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
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
import kotlinx.coroutines.runBlocking
import com.example.oplev.ui.theme.*

@Composable
fun createJourneyComp(createJourneyViewModel: CreateJourneyViewModel, navController: NavController){

    var tag by remember { mutableStateOf("") }
    var Image by remember { mutableStateOf("") }
    var Date by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var Description by remember { mutableStateOf("") }
    var Title by remember { mutableStateOf("") }

    val context = LocalContext.current
    val activity = LocalContext.current as Activity

    Scaffold(
        topBar = { var userName = ""
            runBlocking {
                userName = createJourneyViewModel.getUserName(activity, context)
            }
            com.example.oplev.sites.TopBar("Velkommen $userName")
        },
        content = {
                  Column(modifier = Modifier
                      .fillMaxSize()
                      .verticalScroll(rememberScrollState())) {
                      topScreenLayout(context)
                      inputTextfield("Destination",80,imageVector = Icons.Default.LocationOn, onValueChange = {
                          Title = it
                      }, Title)
                      ExposedDropdownMenu(list = createJourneyViewModel.getCategories(), imageVector = Icons.Default.Info, category) {
                          category = it.title
                      }
                      inputTextfield("Inviter Venner",80, imageVector = Icons.Default.Person, onValueChange = {
                              /*TODO*/
                          },"")
                      datePicker()
                      inputTextfield("Beskriv oplevelsen",height = 150,imageVector = Icons.Default.Menu, onValueChange = {
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
                          Button(
                              onClick = { createJourneyViewModel.createNewJourney(tag, Image, createJourneyViewModel.getCategoryIdFromTitle(category), Date, Description, Title)
                                  navController.navigate(Screen.FrontPageScreen.route)
                              },
                              colors = ButtonDefaults.buttonColors(
                                  backgroundColor = OplevFarve2,
                                  contentColor = Color.Black
                              ),
                              modifier = Modifier.size(130.dp, 40.dp),
                              shape = RoundedCornerShape(50)
                          ) {
                              Text(text = "Gem", fontSize = 18.sp)
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

@Composable
fun datePicker(){
    Row(modifier = Modifier
        .fillMaxWidth()
        .height(80.dp)
        .padding(10.dp)) {


        /* Skal være en datepicker*/ inputFieldNoRow("Fra Dato",80, imageVector = Icons.Default.DateRange)
        Spacer(modifier = Modifier.width(20.dp))
        /* Skal være en "time picker"*/ inputFieldNoRow("Fra kl.",80, imageVector = Icons.Default.DateRange)
    }
    Row(modifier = Modifier
        .fillMaxWidth()
        .height(80.dp)
        .padding(10.dp)) {
        /* Skal være en datepicker*/ inputFieldNoRow("Til Dato",80, imageVector = Icons.Default.DateRange)
        Spacer(modifier = Modifier.width(20.dp))
        /* Skal være en "time picker"*/ inputFieldNoRow("Til kl.",80, imageVector = Icons.Default.DateRange)
    }
}

@Composable
fun topScreenLayout(context: Context){
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val bitmap = remember { mutableStateOf<Bitmap?>(null) }
    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()){ uri: Uri? -> imageUri = uri}

    Box(modifier = Modifier
        .height(120.dp)
        .fillMaxWidth()){
            Image(painter = painterResource(id = R.drawable.img_finland),
                contentDescription = "Placeholder", modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                contentScale = ContentScale.FillBounds
            )
            imageUri?.let{
                if (Build.VERSION.SDK_INT < 28){
                    bitmap.value = MediaStore.Images
                        .Media.getBitmap(context.contentResolver, it)
                } else {
                    val source = ImageDecoder.createSource(context.contentResolver, it)
                    bitmap.value = ImageDecoder.decodeBitmap(source)
                }

                //BTM SKAL GEMMES I EN VARIABEL SOM KAN SMIDES IND I DB!
                bitmap.value?.let{ btm ->
                    Image(bitmap = btm.asImageBitmap(), contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight())
                }
            }
        IconButton(onClick = { launcher.launch("image/*") }, modifier = Modifier
            .align(Alignment.BottomEnd)) {
            Icon(imageVector = Icons.Default.AddCircle, contentDescription ="",
                tint=Color.White
            )
        }
    }
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
                            selectedOptionlocal = selectionOption.title
                            expanded = false
                        }
                    ) {
                        Text(text = selectionOption.title)
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

