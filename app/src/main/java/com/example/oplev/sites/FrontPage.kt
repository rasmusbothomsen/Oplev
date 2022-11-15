package com.example.oplev.sites

import android.graphics.Paint.Align
import android.graphics.Paint.CURSOR_AT_OR_AFTER
import android.widget.Space
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.oplev.Model.Journey
import com.example.oplev.R
import com.example.oplev.Screen
import com.example.oplev.ui.components.Button
import com.example.oplev.ui.components.TextFieldString

@Preview
@Composable
fun FrontPagePrev() {
    BottomBarTest2()
}




@Composable
fun FrontPageScreen(navController: NavController) {
    //Skal hentes fra firebase på et tidspunkt

    val images = listOf(
        R.drawable.img_denmark, R.drawable.img_finland,
        R.drawable.img_norway, R.drawable.img_japan
    )

    val imagesIterator = images.iterator()
    Column(
        modifier = Modifier.verticalScroll(rememberScrollState())
    ) {
        androidx.compose.material.Button(onClick = {
            navController.navigate(Screen.SignUpScreen.route)
        }) {
            Text(text = "DU ER PÅ FORSIDEN")
        }
    }

}


@Composable
fun BottomBarTest() {
    Scaffold(bottomBar = {
        BottomAppBar(
            modifier = Modifier
                .height(65.dp),
            cutoutShape = CircleShape,
        ) {
            BottomNavigation() {
                BottomNavigationItem(
                    icon = { Icon(imageVector = Icons.Default.Home, "") },
                    label = { Text(text = "Hjem") },
                    selected = false,
                    onClick = {})

                BottomNavigationItem(
                    icon = { Icon(imageVector = Icons.Default.Search, "") },
                    label = { Text(text = "Søg") },
                    selected = false,
                    onClick = {})

                BottomNavigationItem(
                    icon = { Icon(imageVector = Icons.Default.List, "") },
                    label = { Text(text = "Idéer") },
                    selected = false,
                    onClick = {})

                BottomNavigationItem(
                    icon = { Icon(imageVector = Icons.Default.AccountCircle, "") },
                    label = { Text(text = "Profil") },
                    selected = false,
                    onClick = {})
            }
        }
    },
        floatingActionButtonPosition = FabPosition.Center,
        isFloatingActionButtonDocked = true,
        floatingActionButton = {
            FloatingActionButton(shape = CircleShape,
                onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "")
            }
        }
    ) {

    }
}

    @Composable
    fun JourneyCard(/*journey: Journey*/) {
        Card(modifier = Modifier
            .clickable { }
            .padding(5.dp, 5.dp, 5.5.dp, 20.dp), elevation = 5.dp, backgroundColor = Color.LightGray) {
            //Nedenunder er padding = størrelse på card.
            Column(modifier = Modifier.padding(0.dp)) {
                Box(modifier = Modifier.padding(0.dp)) {
                    Image(
                        painter = painterResource(id =  /* Her vil vi gerne have journey.GetIMG */R.drawable.img_denmark),
                        contentDescription = "Image Denmark"
                    )
                    Box(
                        modifier = Modifier
                            .size(width = 185.dp, height = 20.dp)
                            .align(Alignment.BottomCenter)
                            .background(Color.Black.copy(alpha = 0.6f))
                    ) {
                        Text(
                            text = "Danmark"/*journey.title*/,
                            modifier = Modifier.align(Alignment.BottomCenter),
                            color = Color.White
                        )
                    }

                }

            }

        }

    }

    @Composable
    fun CategoryRow(title: String) {

        Text(text = title, fontSize = 20.sp, fontFamily = FontFamily.SansSerif)

        Row(modifier = Modifier.horizontalScroll(rememberScrollState())) {
            JourneyCard()

            JourneyCard()

            JourneyCard()

            JourneyCard()

            JourneyCard()

            JourneyCard()
        }

    }

    @Composable
    fun FrontPageColumn() {
        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
            Spacer(modifier = Modifier.height(15.dp))
            CategoryRow("Seneste Ture")
            //Spacer(modifier = Modifier.height(5.dp))
            CategoryRow("Favoritter")
            //Spacer(modifier = Modifier.height(5.dp))
            CategoryRow("Asien")
            //Spacer(modifier = Modifier.height(5.dp))
            CategoryRow("Afrika")
            //Spacer(modifier = Modifier.height(5.dp))
            CategoryRow("Europa")
            //Spacer(modifier = Modifier.height(5.dp))
            CategoryRow("")
            //Spacer(modifier = Modifier.height(5.dp))
            CategoryRow("")
            //Spacer(modifier = Modifier.height(5.dp))
            CategoryRow("")
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
    BottomAppBar(modifier = Modifier.height(65.dp), cutoutShape = CircleShape,) {
        BottomNavigation() {
            BottomNavigationItem(
                icon = { Icon(imageVector = Icons.Default.Menu, "") },
                label = { Text(text = "Menu") },
                selected = false,
                onClick = {})
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
    FloatingActionButton(shape = CircleShape, onClick = { TODO() }) {
        Icon(imageVector = Icons.Filled.Add, contentDescription = "")

    }
}

@Composable
fun BottomBarTest2() {
    Scaffold(
        topBar = { TopBar("Velkommen") },

        content = { FrontPageColumn() },

        bottomBar = { BottomBar()},
        floatingActionButtonPosition = FabPosition.Center, isFloatingActionButtonDocked = true,
        floatingActionButton = { Fab() }






    )









    }

        //floatingActionButtonPosition = FabPosition.Center, isFloatingActionButtonDocked = true,
       // floatingActionButton = { Fab() }





