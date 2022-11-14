package com.example.oplev.ui.components

import android.service.autofill.OnClickAction
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.core.graphics.toColorInt
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.oplev.R
import com.example.oplev.Screen
import com.example.oplev.ViewModel.JourneyViewModel
import com.example.oplev.sites.FrontPageScreen
import com.example.oplev.sites.SignUpScreen

@Composable
fun NavController() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.FrontPageScreen.route) {
        composable(route = Screen.FrontPageScreen.route){
            FrontPageScreen (navController)
        }
        composable(route = Screen.SignUpScreen.route){
            SignUpScreen (navController)
        }
    }


}

@Composable
fun Button(text: String, width: Int, height: Int, hexCode: String, onClick: Unit ){
    Button(
        onClick = {
            onClick
        },
        colors = ButtonDefaults.buttonColors(backgroundColor = Color(hexCode.toColorInt()), contentColor = Color.White),
        modifier = Modifier
            .height(height.dp)
            .width(width.dp),
        shape = RoundedCornerShape(15),
        elevation = ButtonDefaults.elevation(4.dp)
    )

    {
        Text(text = text)
    }
}


//"tekst knap"

@Composable
fun Button(text: String, width: Int, height: Int, onClick: () -> Unit){
    Button(onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.Transparent,
            contentColor = Color.Black), modifier = Modifier
            .height(height.dp)
            .width(width.dp),
        elevation = ButtonDefaults.elevation(0.dp)
    ) {
        Text(text = text,
            modifier = Modifier.alpha(ContentAlpha.medium)
        )
    }
}




@Composable
fun Fab(color: Color, text: String){
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                TODO()
            }, backgroundColor = color,
                modifier = Modifier.border(0.2.dp, Color.Black, CircleShape)) {
                Text(text, fontSize = 30.sp)
            }
        }
    ) {

    }
}


@Composable
fun TextFieldString (text: String, width: Int, height: Int) {
    var text by remember { mutableStateOf(TextFieldValue(text)) }
    TextField(
        value = text,
        onValueChange = { newText ->
            text = newText
        }, modifier = Modifier
            .height(height.dp)
            .width(width.dp), textStyle = TextStyle.Default.copy(fontSize = 18.sp)
    )
}

@Composable
fun TopBar(title: String) {
    TopAppBar(
        title = { Text(title) },

        navigationIcon = {
            IconButton(onClick = {
                TODO()
            })
            {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = "Localized description",
                    tint = Color.White
                )
            }
        },
        actions = {
            IconButton(onClick = {

                TODO() }
            )

            {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Localized description",
                    tint = Color.White
                )
            }
        },
        backgroundColor = Color.LightGray
    )
}

@Composable
fun VerticalScroll( ){
        Column(modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxWidth()
            .fillMaxHeight()){
            Image(painterResource(id = R.drawable.img_norway), "cd" )
            Image(painterResource(id = R.drawable.img_norway), "cd" )
    }
}

@Composable
fun HorizontalScroll( ){
    Row(modifier = Modifier.horizontalScroll(rememberScrollState())) {
        Image(painterResource(id = R.drawable.img_norway), "cd" )
        Image(painterResource(id = R.drawable.img_norway), "cd" )
    }
}

//Den her virker ikke helt som jeg vil ha den til endnu, men tror der er en bedre guide på m3
@Composable
fun ExposedDropdownMenu(){
    var expanded by remember { mutableStateOf(false)
    }
    val list = listOf("Asien", "Europa", "Afrika")
    var selectedItem by remember { mutableStateOf("") }
    var textFilledSize by remember { mutableStateOf(Size.Zero) }
    val icon = if (expanded)
    { Icons.Filled.KeyboardArrowUp }
    else {
        Icons.Filled.KeyboardArrowDown
    }

    OutlinedTextField(value = selectedItem,
        onValueChange = { selectedItem = it },
        modifier = Modifier
            .fillMaxWidth()
            .onGloballyPositioned { coordinates -> textFilledSize = coordinates.size.toSize() },
        label = { Text(text = "select item") },
        trailingIcon = {
            Icon(icon, "", Modifier.clickable { expanded = !expanded })
        }
    )
    DropdownMenu(expanded = expanded,
        onDismissRequest = { expanded = false },
        modifier = Modifier.width(with(LocalDensity.current){textFilledSize.width.toDp()})
    ) {
        list.forEach { label ->
            DropdownMenuItem(onClick = {
                selectedItem = label
                expanded = false
            }) {
                Text(text = label)

            }
        }
    }

}
