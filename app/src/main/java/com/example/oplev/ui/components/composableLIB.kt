package com.example.oplev.ui.components

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.core.graphics.toColorInt
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.oplev.Model.Category
import com.example.oplev.Model.Journey
import com.example.oplev.R
import com.example.oplev.ViewModel.CreateJourneyViewModel
import com.example.oplev.ViewModel.FrontpageViewModel
import com.example.oplev.ViewModel.JourneyViewModel
import com.example.oplev.sites.*
import com.example.oplev.sites.Journy.JourneyScreen
import com.example.oplev.sites.Journy.createJourneyComp


@Composable
fun NavController() {
    val navController = rememberNavController()
    val journey1 = Journey("e","img_denmark",null,"","Danmark", null)
    val journey2 = Journey("e","img_norway",null,"","Norge",null)
    val journey3 = Journey("e","img_finland",null,"","Finland",null)
    val journey4 = Journey("e","img_turkey",null,"","Tyrkiet",null)
    val journeys = listOf(journey1, journey2, journey3, journey4)

    val seneste = Category("Seneste", journeys)
    val favoritter = Category("Favoritter", journeys)
    val mumsesteg = Category("mumsesteg", journeys)
    val categories = listOf(seneste,favoritter, mumsesteg)
    val frontpage = "frontpage"
    val createJourneyPage = "create"
    val journeyPage = "journeypage"

    var testJourney = Journey(tag = "test", image = null, date = null, description = "This is a test", title = "Danmark", folder = null, ideas = null)


    val frontpageViewModel = FrontpageViewModel(categories)

    val createJourneyViewModel = CreateJourneyViewModel()

    createJourneyViewModel.category.categorys.add(favoritter)
    createJourneyViewModel.category.categorys.add(seneste)



    NavHost(navController = navController, startDestination = frontpage) {
        composable(route = frontpage) {
            TotalView(frontpageViewModel = frontpageViewModel, fabNav = {
                navController.navigate(createJourneyPage)
            }, journeyNav = {
                navController.navigate(journeyPage)
            }
            )
        }
        composable(route = createJourneyPage) {
            createJourneyComp(createJourneyViewModel = createJourneyViewModel, nav = {navController.navigate(frontpage)} )
        }
        composable(route = journeyPage){
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

@Preview
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun IdeaGrid() {
    LazyVerticalGrid(
        //Beslut mellem .Adaptive/.Fixed - kun nødvendigt hvis appen skal kunne ses horisontalt
        cells = GridCells.Adaptive(100.dp),
        content = {
            items(100) { i ->
                Box(
                    modifier = Modifier
                        .padding(4.dp)
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
