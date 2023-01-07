package com.example.oplev.sites


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
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import androidx.navigation.NavController
import com.example.oplev.Model.States
import com.example.oplev.R
import com.example.oplev.Screen
import com.example.oplev.ViewModel.AuthViewModel

@Composable
fun ProfileView(authViewModel: AuthViewModel, navController: NavController) {
    val scaffoldstate = rememberScaffoldState()
    val state = authViewModel.state.value
    Scaffold(
        scaffoldState = scaffoldstate,
        topBar = { TopBar("") },
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

        //SKAL HENTE BRUGERNAVN
        val fullname = remember { mutableStateOf("Berfin Flora Turan") }
        var nameEditable = states.nameEditable

        TextField(
            value = fullname.value,
            onValueChange = { fullname.value = it },
            placeholder = { Text(text = "Berfin Flora Turan") },
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
        val phoneNum = remember { mutableStateOf("bfloraturan@gmail.com") }
        var phoneNumEditable = states.phoneNumEditable

        TextField(
            value = phoneNum.value,
            onValueChange = { phoneNum.value = it },
            placeholder = { Text(text = "+45 12 34 56 78") },
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

            TextButton(onClick = { /*TODO*/ }) {
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
            TextButton(onClick = { /*TODO*/ }) {
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
            TextButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Default.Close, contentDescription = "", tint = Color.Black )
                Spacer(modifier = Modifier.width(20.dp))
                Text(text = "Slet konto", modifier = Modifier.padding(top=2.5.dp), color = Color.Black)
                Spacer(modifier = Modifier.width(200.dp))
                Icon(imageVector = Icons.Default.KeyboardArrowRight, contentDescription = "",
                    modifier = Modifier.padding(bottom=15.dp), tint = Color.Black)
            }
        }
        Spacer(modifier = Modifier.height(20.dp))

        val hexCode = "#79B56C"
        Button(onClick = {
            /* TODO */
            navController.navigate(Screen.FrontPageScreen.route)
        }, modifier = Modifier.align(Alignment.CenterHorizontally),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(hexCode.toColorInt()), contentColor = Color.White),
            shape = CircleShape
        ) {
            Text(text = "Gem ændringer")
        }

    }
}



