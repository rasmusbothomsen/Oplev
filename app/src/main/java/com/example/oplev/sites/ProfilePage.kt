package com.example.oplev.sites


import android.app.Activity
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import androidx.navigation.NavController
import com.example.oplev.Model.States
import com.example.oplev.R
import com.example.oplev.Screen
import com.example.oplev.ViewModel.AuthViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.runBlocking

@Composable
fun ProfileView(authViewModel: AuthViewModel, navController: NavController) {
    val scaffoldstate = rememberScaffoldState()
    val state = authViewModel.state.value
    Scaffold(
        scaffoldState = scaffoldstate,
        topBar = { TopBar("",navController) },
        content = { ProfileContent(authViewModel, navController, state) },
    )
}

@Composable
fun ProfileContent(authViewModel: AuthViewModel, navController: NavController, states: States) {
    Column(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)) {
                //Halvcirkel
                Image(
                    painter = painterResource(id = R.drawable.design_uden_navn__9_),
                    contentDescription = "halfcircle",
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.TopCenter)
                )

            Box( modifier = Modifier
                .width(125.dp)
                .height(125.dp)
                .graphicsLayer {
                    translationX = 360f
                    translationY = 160f
                }){
            //https://www.youtube.com/watch?v=ec8YymnjQSE&ab_channel=KBCODER
            var imageUri by remember { mutableStateOf<Uri?>(null) }
            val context = LocalContext.current
            val bitmap = remember { mutableStateOf<Bitmap?>(null) }
            val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()){uri: Uri? -> imageUri = uri}
                Image(painter = painterResource(id = R.drawable.design_uden_navn__8_), contentDescription = null)

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
                    modifier = Modifier.clip(RoundedCornerShape(15.dp)))
                }
            }
                IconButton(onClick = { launcher.launch("image/*") }, modifier = Modifier
                    .align(Alignment.BottomEnd)) {
                    Icon(imageVector = Icons.Default.AddCircle, contentDescription = null, tint = Color.White)
                }
            }

        }
        var fullNameFromDb = " "
        runBlocking {
           fullNameFromDb = authViewModel.getFullName()
        }

        var phoneNumFromDb = " "
        runBlocking {
            phoneNumFromDb = authViewModel.getPhoneNum()
        }

        //SKAL HENTE BRUGERNAVN
        val fullname = remember { mutableStateOf(fullNameFromDb) }
        var nameEditable = states.nameEditable

        TextField(
            value = fullname.value,
            onValueChange = { fullname.value = it },
            placeholder = { Text(text = fullNameFromDb) },
            modifier = Modifier
                .padding(all = 16.dp)
                .fillMaxWidth()
                .border(1.dp, Color.LightGray, CircleShape),
            shape = CircleShape,
            colors = TextFieldDefaults.textFieldColors(
                textColor = Color.Black,
                disabledTextColor = Color.Gray,
                backgroundColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
        trailingIcon = {
            IconButton(onClick = { authViewModel.nameStateChange() }) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = "" )
            }
        },
        enabled = nameEditable)

        //SKAL HENTE MAIL FRA DB
        val phoneNum = remember { mutableStateOf(phoneNumFromDb) }
        var phoneNumEditable = states.phoneNumEditable

        TextField(
            value = phoneNum.value,
            onValueChange = { phoneNum.value = it },
            placeholder = { Text(text = phoneNumFromDb) },
            modifier = Modifier
                .padding(all = 16.dp)
                .fillMaxWidth()
                .border(1.dp, Color.LightGray, CircleShape),
            shape = CircleShape,
            colors = TextFieldDefaults.textFieldColors(
                textColor = Color.Black,
                disabledTextColor = Color.Gray,
                backgroundColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            trailingIcon = {
                IconButton(onClick = { authViewModel.PhoneStateChange() }) {
                    Icon(imageVector = Icons.Default.Edit, contentDescription = "" )
                }
            },
            enabled = phoneNumEditable)

        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 16.dp, 16.dp, 0.dp)) {

            TextButton(onClick = {
                var email = Firebase.auth.currentUser?.email

                runBlocking {
                    if (email != null) {
                        authViewModel.sendPasswordReset(email)
                    }
                    authViewModel.forgotPasswordStateChange()
                }
            }) {
                Icon(imageVector = Icons.Default.Lock, contentDescription = "", tint = Color.Black )
                Spacer(modifier = Modifier.width(20.dp))
                Text(text = "Skift adgangskode", modifier = Modifier.padding(top=2.5.dp), color = Color.Black)
                Spacer(modifier = Modifier.width(140.dp))
                Icon(imageVector = Icons.Default.KeyboardArrowRight, contentDescription = "",
                    modifier = Modifier.padding(bottom=15.dp), tint = Color.Black)
            }

        }
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 16.dp, 16.dp, 0.dp)) {
            TextButton(onClick = {
                authViewModel.changeDialogVal()
            }) {
                Icon(imageVector = Icons.Default.MailOutline, contentDescription = "", tint = Color.Black )
                Spacer(modifier = Modifier.width(20.dp))
                Text(text = "Skift mail", modifier = Modifier.padding(top=2.5.dp), color = Color.Black)
                Spacer(modifier = Modifier.width(205.dp))
                Icon(imageVector = Icons.Default.KeyboardArrowRight, contentDescription = "",
                    modifier = Modifier.padding(bottom=15.dp), tint = Color.Black)
            }
        }
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 16.dp, 16.dp, 0.dp)) {
            TextButton(onClick = {
                runBlocking {
                    authViewModel.deleteUser()
                }
                if (FirebaseAuth.getInstance().currentUser == null) {
                    navController.navigate(Screen.LoginScreen.route)
                }
            }) {
                Icon(imageVector = Icons.Default.Close, contentDescription = "", tint = Color.Black )
                Spacer(modifier = Modifier.width(20.dp))
                Text(text = "Slet konto", modifier = Modifier.padding(top=2.5.dp), color = Color.Black)
                Spacer(modifier = Modifier.width(200.dp))
                Icon(imageVector = Icons.Default.KeyboardArrowRight, contentDescription = "",
                    modifier = Modifier.padding(bottom=15.dp), tint = Color.Black)
            }
        }
        Spacer(modifier = Modifier.height(20.dp))

        var context = LocalContext.current
        var activity = LocalContext.current as Activity

        val hexCode = "#79B56C"
        Button(onClick = {
            var phoneNumber: String
            runBlocking {
                phoneNumber = authViewModel.getPhoneNum()
            }
            if (!phoneNum.equals(phoneNumber)) {
                runBlocking {
                    authViewModel.updatePhoneNum(phoneNum.value, activity)
                }
                var fullName: String
                runBlocking {
                    fullName = authViewModel.getFullName()
                }
                if (!fullname.equals(fullName)) {
                    runBlocking {
                        authViewModel.updateName(fullname.value, activity)
                    }
                }
                navController.navigate(Screen.FrontPageScreen.route)
            }
        }, modifier = Modifier.align(Alignment.CenterHorizontally),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(hexCode.toColorInt()), contentColor = Color.White),
            shape = CircleShape
        ) {
            Text(text = "Gem ændringer")
        }

        if(states.emailDialogState) {
            NewEmailDialog(authViewModel)
        }

        //TODO ER TAGET FRA ASOS. SKAL ÆNDRES
        if (states.forgotpassworddialog) {
            AlertDialog(
                onDismissRequest = { /*TODO*/ },
                title = { Text(text = "LINK TIL NULSTILLING AF ADGANGSKODE ER SENDT") },
                text = {
                    Text(
                        text =
                        "Vi har sendt dig en e-mail til nulstilling af din adgangskode\n" +
                                "\n" +
                                "For at oprette din nye adgangskode skal du klikke på linket i e-mailen og angive en ny – pærenemt\n" +
                                "\n" +
                                "Har du ikke modtaget e-mailen? Tjek din spammappe."
                    )
                },
                confirmButton = {
                    Button(onClick = {
                        authViewModel.forgotPasswordStateChange()
                    }) {
                        Text(text = "OK")
                    }
                }
            )
        }
    }

}

@Composable
fun NewEmailDialog(authViewModel: AuthViewModel){
    var email by remember { mutableStateOf("") }
    val activity = LocalContext.current as Activity
    AlertDialog(
        onDismissRequest = { /*TODO*/ },
        title = { Text(text = "Indtast din nye mail")},
        text = {
            OutlinedTextField(
                value = email,
                label = { Text(text = "Mail", textAlign = TextAlign.Center) },
                modifier = Modifier
                    .width(130.dp),
                onValueChange = {
                    email = it
                },
                singleLine = true
            )
        },
        confirmButton = {
            Button(onClick = {
                runBlocking {
                    authViewModel.updateEmail(email)
                }
                authViewModel.changeDialogVal()
            }) {
                Text(text = "Gem")
            }
        },
        dismissButton = {
            Button(onClick = {
                authViewModel.changeDialogVal()
            }) {
                Text(text = "Annuller")
            }
        }
    )
}



