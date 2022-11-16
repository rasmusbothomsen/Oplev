package com.example.oplev.sites

import android.content.res.Resources
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.oplev.Model.Category
import com.example.oplev.Model.Journey
import com.example.oplev.R
import com.example.oplev.Screen
import com.example.oplev.ViewModel.FrontpageViewModel

@Preview
@Composable
fun FrontPagePrev() {
    val journey1 = Journey("e","img_denmark",null,"","Danmark", null)
    val journey2 = Journey("e","img_norway",null,"","Norge",null)
    val journey3 = Journey("e","img_finland",null,"","Finland",null)
    val journey4 = Journey("e","img_turkey",null,"","Tyrkiet",null)
    val journeys = listOf(journey1, journey2, journey3, journey4)

    val seneste = Category("Seneste", journeys)
    val favoritter = Category("Favoritter", journeys)
    val categories = listOf(seneste,favoritter)

    val frontpageViewModel = FrontpageViewModel(categories)

    TotalView(frontpageViewModel = frontpageViewModel)
}

/**@Composable
fun MenuDrawer(){
    val drawerState = rememberDrawerState(DrawerValue.Open /*Skal selvfølgelig være lukket til at starte med */)
    ModalDrawer(
        drawerState = drawerState,
        drawerContent = {
            Box(modifier = Modifier.size(360.dp, 180.dp)) {
                Text(text = "Hello")
            }
        })
}
**/

@Composable
fun TotalView(frontpageViewModel: FrontpageViewModel) {
    Scaffold(
        drawerContent = {

        },
        drawerGesturesEnabled = true,
        topBar = { TopBar("Velkommen") },
        content = { FrontPageColumn(frontpageViewModel.categories) },
        bottomBar = { BottomBar()},
        floatingActionButtonPosition = FabPosition.Center, isFloatingActionButtonDocked = true,
        floatingActionButton = { Fab() },
    )
}

@Composable
fun FrontPageColumn(categories : List<Category>) {
    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        val max = categories.size-1

        for (i in 0..max) {
            Spacer(modifier = Modifier.height(15.dp))
            CategoryRow(categories[i])
        }

    }
}

@Composable
fun CategoryRow(category: Category) {
    val max = category.journeys.size-1

    Text(text = category.title, fontSize = 20.sp, fontFamily = FontFamily.Default, fontWeight = FontWeight.Medium, modifier = Modifier.padding(5.dp, 0.dp, 0.dp, 0.dp))

    Row(modifier = Modifier.horizontalScroll(rememberScrollState())) {

        for (i in 0..max) {
            JourneyCard(category.journeys[i])
        }
    }

}

@Composable
fun JourneyCard(journey: Journey) {
    val img = journey.image
    val context = LocalContext.current
    val drawableId = remember(img) {
        context.resources.getIdentifier(
            img,
            "drawable",
            context.packageName
        )
    }

        Card(modifier = Modifier
            .clickable { }
            .padding(5.dp, 1.3.dp, 5.5.dp, 15.dp), elevation = 5.dp, backgroundColor = Color.LightGray) {
            //Nedenunder er padding = størrelse på card.
            Column(modifier = Modifier.padding(0.dp)) {
                Box(modifier = Modifier.padding(0.dp)) {
                    Image(
                        painter = painterResource(id = drawableId),
                        contentDescription = "Image Denmark"
                    )
                    Box(
                        modifier = Modifier
                            .size(width = 185.dp, height = 23.dp)
                            .align(Alignment.BottomCenter)
                            .background(Color.Black.copy(alpha = 0.6f))
                    ) {
                        Text(
                            fontSize = 18.sp,
                            text = journey.title,
                            modifier = Modifier.align(Alignment.BottomCenter),

                            color = Color.White
                        )
                    }

                }

            }

        }

    }

@Composable
fun TopBar(title: String) {
            TopAppBar( modifier = Modifier.height(65.dp),
                title = { Text(title) },

                navigationIcon = {
                    IconButton(onClick = {
                        TODO()
                    })
                    {
                        Icon(
                            painter = painterResource(id = R.drawable.oplev72dpi),
                            contentDescription = ""
                        )


                    }
                },


                backgroundColor = Color.LightGray
            )
        }

@Composable
fun BottomBar(){
    BottomAppBar(modifier = Modifier.height(65.dp) /*cutoutShape = CircleShape,*/) {
        BottomNavigation {
            BottomNavigationItem(
                icon = { Icon(imageVector = Icons.Default.Menu, "") },
                label = { Text(text = "Menu") },
                selected = false,
                onClick = { })
            BottomNavigationItem(
                icon = { Icon(imageVector = Icons.Default.Search, "") },
                label = { Text(text = "Søg") },
                selected = false,
                onClick = {})
        }
    }
}

@Composable
fun Fab(){
    FloatingActionButton(shape = CircleShape, modifier = Modifier.size(width = 75.dp, height = 75.dp), onClick = { TODO() }) {
        Icon(imageVector = Icons.Filled.Add, contentDescription = "", modifier = Modifier.size(38.dp), tint = Color.White)

    }
}

@Composable
fun FrontPageScreen(navController: NavController) {
    //Skal hentes fra firebase på et tidspunkt

    Column(
        modifier = Modifier.verticalScroll(rememberScrollState())
    ) {
        Button(onClick = {
            navController.navigate(Screen.SignUpScreen.route)
        }) {
            Text(text = "DU ER PÅ FORSIDEN")
        }
    }

}







