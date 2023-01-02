package com.example.oplev.ui.components

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
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
import com.example.oplev.DependencyController
import com.example.oplev.MainActivity
import com.example.oplev.Model.Journey
import com.example.oplev.R
import com.example.oplev.Screen
import com.example.oplev.ViewModel.CreateJourneyViewModel
import com.example.oplev.ViewModel.FrontPageViewModel
import com.example.oplev.ViewModel.JourneyViewModel
import com.example.oplev.sites.*
import com.example.oplev.sites.Journy.JourneyScreen
import com.example.oplev.sites.Journy.createJourneyComp
import kotlinx.coroutines.MainScope


@Composable
fun NavController() {
    val navController = rememberNavController()
    var dependencyController = DependencyController()
    dependencyController.initializeData()

    val frontpageViewModel = FrontPageViewModel(dependencyController.categoryData)

    val createJourneyViewModel = CreateJourneyViewModel(dependencyController.journeyData,dependencyController.categoryData)

    var testJourney = Journey(tag = "test", image = null, date = null, description = "This is a test", title = "Danmark", folder = null, ideas = null)






    NavHost(navController = navController, startDestination = Screen.FrontPageScreen.route) {
        composable(route = Screen.FrontPageScreen.route) {
            TotalView(frontpageViewModel = frontpageViewModel, navController)
        }
        composable(route = Screen.CreateJourneyScreen.route) {
            createJourneyComp(createJourneyViewModel = createJourneyViewModel, navController )
        }
        composable(route = Screen.JourneyScreen.route){
            JourneyScreen(journeyViewModel = JourneyViewModel(testJourney))
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

/*
@Preview
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun IdeaGridd(padding: Int) {
    LazyVerticalGrid(
        //Beslut mellem .Adaptive/.Fixed - kun nødvendigt hvis appen skal kunne ses horisontalt
        cells = GridCells.Adaptive(100.dp),
        content = {
            items(10) { i ->
                Box(
                    modifier = Modifier
                        .padding(padding.dp)
                        .aspectRatio(1f)
                        .clip(RoundedCornerShape(30.dp))
                        .background(Color.Blue),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Idea $i")
                }

            }
        })
}

 */

@Composable
fun FolderGrid() {
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
