package com.example.oplev.sites.Journy

import CreateJourneyViewModel
import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.Alignment
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.oplev.Model.Category
import com.example.oplev.R
import com.example.oplev.Screen
import kotlinx.coroutines.runBlocking
import com.example.oplev.ui.theme.*
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import compose.icons.LineAwesomeIcons
import compose.icons.lineawesomeicons.*

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun createJourneyComp(createJourneyViewModel: CreateJourneyViewModel, navController: NavController){

    var tag by remember { mutableStateOf("") }
    var Image by remember { mutableStateOf("") }
    var Date by remember { mutableStateOf("") }
    var category by remember { mutableStateOf(createJourneyViewModel.getCategories()[1].title) }
    var Description by remember { mutableStateOf("") }
    var Title by remember { mutableStateOf("") }
    var collaboratorIds = mutableListOf<String>()
    var collaboratorId by remember { mutableStateOf("") }
    var dstate = mutableStateOf(false)
    var invitedialog = mutableStateOf(false)
    var blur = mutableStateOf(0.dp)

    val context = LocalContext.current
    val activity = LocalContext.current as Activity

    Scaffold(
        topBar = { var userName = ""
            runBlocking {
                userName = createJourneyViewModel.getUserName(activity, context)
            }
            com.example.oplev.sites.TopBar("Velkommen $userName", navController)
        },
        content = {
                  Column(modifier = Modifier
                      .fillMaxSize()
                      .verticalScroll(rememberScrollState())
                      .blur(blur.value)) {

                      topScreenLayout(context, createJourneyViewModel)

                      OutlinedTextField(
                          value = Title,
                          label = { Text(text = "Destination") },
                          modifier = Modifier
                              .width(300.dp)
                              .align(Alignment.CenterHorizontally),
                          onValueChange = { Title = it },
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
                                  imageVector = LineAwesomeIcons.MapMarkerSolid,
                                  contentDescription = "",
                                  tint = Farvekombi032
                              )
                          },
                      )

                      Row(
                          modifier = Modifier
                              .width(300.dp)
                              .align(Alignment.CenterHorizontally)
                      ) {
                          ExposedDropdownMenu(
                              list = createJourneyViewModel.getCategories(),
                              category
                          ) {
                              category = it.title
                          }
                      }

                      OutlinedTextField(
                          value = collaboratorId,
                          label = { Text(text = "Inviter venner") },
                          modifier = Modifier
                              .width(300.dp)
                              .align(Alignment.CenterHorizontally)
                              .clickable(onClick = {
                                  invitedialog.value = true
                                  blur.value = 16.dp
                              }),
                          enabled = false,
                          onValueChange = { collaboratorId = it},
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
                                  imageVector = LineAwesomeIcons.UserPlusSolid,
                                  contentDescription = "",
                                  tint = Farvekombi032
                              )
                          },
                      )

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

                      val maxChar = 150

                      Column(
                          modifier = Modifier
                              .width(300.dp)
                              .align(Alignment.CenterHorizontally)
                      ) {

                          OutlinedTextField(
                              value = Description,
                              onValueChange = {
                                  if (it.length <= maxChar) Description = it
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
                                      LineAwesomeIcons.PenSolid,
                                      contentDescription = "",
                                      tint = Farvekombi032,
                                      modifier = Modifier.padding(bottom = 135.dp)
                                  )
                              }
                          )

                          Text(
                              text = "${Description.length} / $maxChar",
                              textAlign = TextAlign.End,
                              style = MaterialTheme.typography.caption,
                              modifier = Modifier
                                  .width(300.dp)
                                  .padding(10.dp, 0.dp, 16.dp, 5.dp)
                          )
                      }

                      Row(modifier = Modifier.padding(50.dp, 10.dp, 0.dp, 80.dp)) {
                          //Nedenstående buttons skal være composables
                          TextButton(
                              onClick = {
                                  navController.navigate(Screen.FrontPageScreen.route)
                              },
                              modifier = Modifier.size(130.dp, 40.dp),
                              shape = RoundedCornerShape(50)
                          ) {
                              Text(
                                  text = "Annuller", fontSize = 18.sp, color = Color.Gray
                              )
                          }
                          Spacer(modifier = Modifier.width(40.dp))
                          Button(
                              onClick = {
                                  createJourneyViewModel.createNewJourney(tag, Image, createJourneyViewModel.getCategoryIdFromTitle(category), Date, Description, Title, collaboratorIds, activity)
                                  navController.navigate(Screen.FrontPageScreen.route)
                              },
                              colors = ButtonDefaults.buttonColors(
                                  backgroundColor = Farvekombi032,
                                  contentColor = Color.White
                              ),
                              modifier = Modifier.size(130.dp, 40.dp),
                              shape = RoundedCornerShape(50)
                          ) {
                              Text(text = "Gem", fontSize = 18.sp)
                          }
                      }

                  }

            if (invitedialog.value) {
                var tempIds = mutableListOf<String>()
                var collab1 by remember { mutableStateOf("") }
                var collab2 by remember { mutableStateOf("") }
                var collab3 by remember { mutableStateOf("") }
                var collab4 by remember { mutableStateOf("") }
                var collab5 by remember { mutableStateOf("") }

                AlertDialog(
                    onDismissRequest = {/*TODO*/ },
                    confirmButton = {
                        Button(onClick = {
                            tempIds.add(collab1)
                            tempIds.add(collab2)
                            tempIds.add(collab3)
                            tempIds.add(collab4)
                            tempIds.add(collab5)

                            for ( i in 0 until tempIds.size){
                                if (tempIds[i].isNotEmpty()){
                                    collaboratorIds.add(tempIds[i])
                                    invitedialog.value = false
                                }
                            }

                            blur.value = 0.dp
                        },
                        shape = CircleShape,
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = Farvekombi032,
                                contentColor = Color.White
                            ),
                            modifier = Modifier.width(100.dp)
                        ) {
                            Text(text = "Inviter")
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = {
                            invitedialog.value = false
                            blur.value = 0.dp
                        },
                        ) {
                            Text(text = "Annuller", color = Color.Black)
                        }
                    },
                    title = {
                        Text(text = "Inviter venner!")
                    },
                    modifier = Modifier
                        .height(500.dp),
                    shape = RoundedCornerShape(25.dp)
                    ,
                    text = {
                        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                            Text(text = "")
                            var stateOfTextfield1 = "Mail 1"
                            OutlinedTextField(
                                value = collab1,
                                label = { Text(text = stateOfTextfield1) },
                                modifier = Modifier
                                    .width(200.dp),
                                onValueChange = { collab1 = it },
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
                                        imageVector = LineAwesomeIcons.UserPlusSolid,
                                        contentDescription = "",
                                        tint = Farvekombi032
                                    )
                                },
                            )

                            var stateOfTextfield2 = "Mail 2"
                            OutlinedTextField(
                                value = collab2,
                                label = { Text(text = stateOfTextfield2) },
                                modifier = Modifier
                                    .width(200.dp)
                                    .align(Alignment.CenterHorizontally),
                                onValueChange = { collab2 = it },
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
                                        imageVector = LineAwesomeIcons.UserPlusSolid,
                                        contentDescription = "",
                                        tint = Farvekombi032
                                    )
                                },
                            )

                            var stateOfTextfield3 = "Mail 3"
                            OutlinedTextField(
                                value = collab3,
                                label = { Text(text = stateOfTextfield3) },
                                modifier = Modifier
                                    .width(200.dp)
                                    .align(Alignment.CenterHorizontally),
                                onValueChange = { collab3 = it },
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
                                        imageVector = LineAwesomeIcons.UserPlusSolid,
                                        contentDescription = "",
                                        tint = Farvekombi032
                                    )
                                },
                            )

                            var stateOfTextfield4 = "Mail 4"
                            OutlinedTextField(
                                value = collab4,
                                label = { Text(text = stateOfTextfield4) },
                                modifier = Modifier
                                    .width(200.dp)
                                    .align(Alignment.CenterHorizontally),
                                onValueChange = { collab4 = it },
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
                                        imageVector = LineAwesomeIcons.UserPlusSolid,
                                        contentDescription = "",
                                        tint = Farvekombi032
                                    )
                                },
                            )

                            var stateOfTextfield5 = "Mail 5"
                            OutlinedTextField(
                                value = collab5,
                                label = { Text(text = stateOfTextfield5) },
                                modifier = Modifier
                                    .width(200.dp)
                                    .align(Alignment.CenterHorizontally),
                                onValueChange = { collab5 = it },
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
                                        imageVector = LineAwesomeIcons.UserPlusSolid,
                                        contentDescription = "",
                                        tint = Farvekombi032
                                    )
                                },
                            )

                        }

                    }

                )
            }
        },
    )
}

@Composable
fun inputTextfield(label: String, height: Int, imageVector: ImageVector, onValueChange: (String) -> Unit, input: String){
    Row(modifier = Modifier
        .fillMaxWidth()
        .height(height.dp)
        .padding(10.dp, 0.dp, 0.dp, 5.dp)){
        Icon(
            imageVector = imageVector,
            contentDescription = "",
        modifier = Modifier.padding(10.dp,20.dp,15.dp,0.dp))
        OutlinedTextField(
            value = input,
            label = { Text(text = label) },
            modifier = Modifier
                .height(height.dp)
                .width(300.dp),
            onValueChange = onValueChange,
            colors = TextFieldDefaults.textFieldColors(
                textColor = Color.Black,
                backgroundColor = Color.White,
                focusedIndicatorColor = Color.DarkGray,
                unfocusedIndicatorColor = Color.DarkGray,
                disabledIndicatorColor = Color.DarkGray
            ),
            shape = CircleShape
        )
    }
}

@Composable
fun inputFieldNoRow(label: String, height: Int, imageVector: ImageVector){
    var input by remember { mutableStateOf("") }
    Icon(
        imageVector = imageVector,
        contentDescription = "",
        modifier = Modifier.padding(10.dp,15.dp,15.dp,5.dp))
    OutlinedTextField(
        value = input,
        label = { Text(text = label) },
        onValueChange = {
            input = it
        }, modifier = Modifier.width(105.dp),
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color.Black,
            backgroundColor = Color.White,
            focusedIndicatorColor = Color.DarkGray,
            unfocusedIndicatorColor = Color.DarkGray,
            disabledIndicatorColor = Color.DarkGray
        ),
        shape = CircleShape
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
fun topScreenLayout(context: Context, createJourneyViewModel: CreateJourneyViewModel){
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
                    createJourneyViewModel.imageState = btm
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
fun ExposedDropdownMenu(list: List<Category>, selectedOption:String, upDateValue: (Category) -> Unit){
    var expanded by remember { mutableStateOf(false) }
    var selectedOptionlocal by remember { mutableStateOf(selectedOption) }
    val icon = if (!expanded) {
        Icons.Filled.KeyboardArrowUp
    } else {
        Icons.Filled.KeyboardArrowDown
    }

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            }
        ) {
            OutlinedTextField(
                value = selectedOptionlocal,
                readOnly = true,
                modifier = Modifier
                    .width(300.dp),
                onValueChange = { selectedOptionlocal = it },
                colors = TextFieldDefaults.textFieldColors(
                    textColor = Color.Black,
                    backgroundColor = Color.White,
                    focusedIndicatorColor = Color.Black,
                    unfocusedIndicatorColor = Color.DarkGray,
                    disabledIndicatorColor = Color.DarkGray
                ),
                shape = CircleShape,
                label = { Text(text = "Kategori") },
                trailingIcon = {
                    Icon(icon, "", Modifier.clickable { expanded = !expanded })
                },
                leadingIcon = {
                    Icon(
                        imageVector = LineAwesomeIcons.TagSolid,
                        contentDescription = "",
                        tint = Farvekombi032
                    )
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

@Preview
@Composable
fun InviteDialog() {

    AlertDialog(
        onDismissRequest = {/*TODO*/ },
        confirmButton = {
            Button(onClick = {
            /*TODO*/
            }) {

            }
        },
        dismissButton = {
            Button(onClick = {
            /*TODO*/
            }) {

            }
        },
        title = {
            Text(text = "Inviter venner!")
        },
        text = {
            var numOfCollaborators by remember { mutableStateOf(1) }
            var stateOfTextfield = "Mail"
            var collab by remember { mutableStateOf("") }


            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {

                OutlinedTextField(
                    value = collab,
                    label = { Text(text = stateOfTextfield) },
                    modifier = Modifier
                        .width(200.dp),
                    //  .align(Alignment.CenterHorizontally),
                    onValueChange = { collab = it },
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
                            imageVector = LineAwesomeIcons.UserPlusSolid,
                            contentDescription = "",
                            tint = Farvekombi032
                        )
                    },
                )

                for (i in 1 until numOfCollaborators) {
                    var stateOfTextfield = "Mail"
                    var collab by remember { mutableStateOf("") }
                    Row() {
                        OutlinedTextField(
                            value = collab,
                            label = { Text(text = stateOfTextfield) },
                            modifier = Modifier
                                .width(200.dp),
                            //  .align(Alignment.CenterHorizontally),
                            onValueChange = { collab = it },
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
                                    imageVector = LineAwesomeIcons.UserPlusSolid,
                                    contentDescription = "",
                                    tint = Farvekombi032
                                )
                            },
                        )
                    }
                }

                Row() {
                    TextButton(
                        onClick = {
                            numOfCollaborators++
                                  },
                        modifier = Modifier
                            .width(300.dp),
                        //  .align(Alignment.CenterHorizontally),
                    ) {
                        Text(text = "Tilføj")
                        Icon(
                            imageVector = Icons.Default.Add, contentDescription = ""
                            // align at end
                        )
                    }
                }




            }

        }

    )

}


@OptIn(ExperimentalMaterialApi::class)
@Preview(showBackground = true)
@Composable
fun createJourneyPreview(){
    var tag by remember { mutableStateOf("") }
    var Image by remember { mutableStateOf("") }
    var Date by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var Description by remember { mutableStateOf("") }
    var Title by remember { mutableStateOf("") }
    var collaboratorId by remember { mutableStateOf("") }
    var list = listOf<String>()
    var dstate = mutableStateOf(false)
    var blur = mutableStateOf(0.dp)


    Scaffold(
        topBar = {
                 TopAppBar() {

                 }
        },
        content = {
            Column(modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .blur(blur.value)) {
                var imageUri by remember { mutableStateOf<Uri?>(null) }
                val bitmap = remember { mutableStateOf<Bitmap?>(null) }
                val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()){ uri: Uri? -> imageUri = uri}

                Box(modifier = Modifier
                    .height(120.dp)
                    .fillMaxWidth()) {
                    Image(
                        painter = painterResource(id = R.drawable.img_finland),
                        contentDescription = "Placeholder", modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(),
                        contentScale = ContentScale.FillBounds
                    )
                    IconButton(onClick = { }) {
                        Icon(
                            imageVector = Icons.Default.AddCircle, contentDescription = "",
                            tint = Color.White
                        )
                    }
                }

                    OutlinedTextField(
                        value = Title,
                        label = { Text(text = "Destination") },
                        modifier = Modifier
                            .width(300.dp)
                            .align(CenterHorizontally),
                        onValueChange = {  },
                        colors = TextFieldDefaults.textFieldColors(
                            textColor = Color.Black,
                            backgroundColor = Color.White,
                            focusedIndicatorColor = Color.Black,
                            unfocusedIndicatorColor = Color.DarkGray,
                            disabledIndicatorColor = Color.DarkGray
                        ),
                        shape = CircleShape,
                        leadingIcon = {
                            Icon(imageVector = Icons.Default.PushPin,
                                contentDescription = "",
                                tint = Farvekombi032
                            )
                        },
                    )

                var expanded by remember { mutableStateOf(false) }
                var selectedOptionlocal by remember { mutableStateOf(category) }
                val icon = if (!expanded)
                {
                    Icons.Filled.KeyboardArrowUp
                }
                else
                {
                    Icons.Filled.KeyboardArrowDown
                }
                Row(modifier = Modifier
                    .width(300.dp)
                    .align(CenterHorizontally)) {
                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = {
                            expanded = !expanded
                        }
                    ) {
                        OutlinedTextField(
                            value = selectedOptionlocal,
                            readOnly = true,
                            modifier = Modifier
                                .width(300.dp),
                            onValueChange = {  },
                            colors = TextFieldDefaults.textFieldColors(
                                textColor = Color.Black,
                                backgroundColor = Color.White,
                                focusedIndicatorColor = Color.Black,
                                unfocusedIndicatorColor = Color.DarkGray,
                                disabledIndicatorColor = Color.DarkGray
                            ),
                            shape = CircleShape,
                            label = { Text(text = "Kategori") },
                            trailingIcon = {
                                Icon(icon, "", Modifier.clickable { expanded = !expanded })
                            },
                            leadingIcon = {
                                Icon(imageVector = Icons.Default.TravelExplore,
                                    contentDescription = "",
                                    tint = Farvekombi032
                                )
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

                                    }
                                ) {
                                    Text(text = "")
                                }
                            }
                        }
                    }
                }

                OutlinedTextField(
                    value = Title,
                    label = { Text(text = "Inviter venner") },
                    modifier = Modifier
                        .width(300.dp)
                        .align(CenterHorizontally),
                    onValueChange = {  },
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
                            imageVector = LineAwesomeIcons.UserPlusSolid,
                            contentDescription = "",
                            tint = Farvekombi032
                        )
                    },
                )

                val dialogState = rememberMaterialDialogState(false)

                    var input by remember { mutableStateOf("") }

                        OutlinedTextField(
                            value = input,
                            label = { Text(text = "Dato") },
                            onValueChange = {
                                input = it
                                dialogState.show()
                            }, modifier = Modifier
                                .width(300.dp)
                                .clickable(onClick = { dstate.value = true })
                                .align(CenterHorizontally),
                            readOnly = true,
                            colors = TextFieldDefaults.textFieldColors(
                                textColor = Color.Black,
                                backgroundColor = Color.White,
                                focusedIndicatorColor = Color.Black,
                                unfocusedIndicatorColor = Color.Black,
                                disabledIndicatorColor = Color.Black,
                                disabledTextColor = Color.Black,
                                disabledPlaceholderColor = Color.Black,
                                disabledLabelColor = Color.Black
                            ),
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.DateRange,
                                    contentDescription = "",
                                    tint = Farvekombi032
                                )
                            },
                            shape = CircleShape)

                        Spacer(modifier = Modifier.width(20.dp))


                if(dstate.value){
                    dialogState.show()
                }

                val maxChar = 150

                Column(modifier = Modifier
                    .width(300.dp)
                    .align(CenterHorizontally)) {

                    OutlinedTextField(
                        value = Description,
                        onValueChange = {
                            if (it.length <= maxChar) Description = it
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
                            .align(CenterHorizontally)
                            .height(200.dp),
                        leadingIcon = {
                            Icon(
                                imageVector = LineAwesomeIcons.Comment,
                                contentDescription = "",
                                tint = Farvekombi032,
                                modifier = Modifier.padding(bottom = 135.dp)
                            )
                        }
                    )

                    Text(
                        text = "${Description.length} / $maxChar",
                        textAlign = TextAlign.End,
                        style = MaterialTheme.typography.caption,
                        modifier = Modifier
                            .width(300.dp)
                            .padding(10.dp, 0.dp, 16.dp, 5.dp)
                    )
                }




                MaterialDialog(
                    dialogState = dialogState,
                    buttons = {
                        positiveButton(text = "Ok", onClick = {dstate.value = false} )
                        negativeButton(text = "Annuller", onClick = {dstate.value = false} )
                    }
                ) {
                    datepicker { date ->
                        // Do stuff with java.time.LocalDate object which is passed in
                    }
                }

                //dialogState.show()


                Row(modifier = Modifier.padding(60.dp,10.dp,0.dp,80.dp)) {
                    //Nedenstående buttons skal være composables
                    TextButton(
                        onClick = {
                        },
                        colors = ButtonDefaults.buttonColors(
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
                        },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = OplevFarve2,
                            contentColor = Color.White
                        ),
                        modifier = Modifier.size(130.dp, 40.dp),
                        shape = RoundedCornerShape(50)
                    ) {
                        Text(text = "Gem", fontSize = 18.sp)
                    }
                }
            }
        }
    )
}


