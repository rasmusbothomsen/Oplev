package com.example.oplev.sites

import android.app.Activity
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Bottom
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import androidx.navigation.NavController
import com.example.oplev.Model.Category
import com.example.oplev.data.dto.CategoryDto
import com.example.oplev.Model.Journey
import com.example.oplev.Model.States
import com.example.oplev.R
import com.example.oplev.Screen
import com.example.oplev.ViewModel.FrontPageViewModel
import com.example.oplev.ui.theme.Farvekombi032
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import com.example.oplev.ui.theme.OplevFarve2
import com.example.oplev.ui.theme.Farvekombi033
import com.example.oplev.ui.theme.OplevBlue


@Preview
@Composable
fun FrontPagePrev() {



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
fun TotalView(frontpageViewModel: FrontPageViewModel, navController: NavController) {
    val scaffoldstate = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val state = frontpageViewModel.state.value
    val context = LocalContext.current
    val activity = LocalContext.current as Activity


    Scaffold(
        scaffoldState = scaffoldstate,
            drawerContent = {

                Row(Modifier.padding(20.dp, 30.dp, 0.dp, 0.dp)) {
                    Image(
                        painter = painterResource(id = R.drawable.oplev300dpi),
                        contentDescription = "Bare et logo",
                        modifier = Modifier
                            .size(85.dp, 85.dp)
                            .padding(0.dp, 0.dp, 0.dp, 0.dp)
                    )
                    Text(
                        text = "OPLEV Menu",
                        modifier = Modifier.padding(0.dp, 25.dp, 0.dp, 0.dp),
                        fontSize = 20.sp
                    )

                }
                Row(Modifier.padding(20.dp, 20.dp, 0.dp, 0.dp)) {
                    Icon(
                        imageVector = Icons.Default.AccountCircle,
                        contentDescription = "",
                        modifier = Modifier
                            .size(45.dp, 45.dp)
                            .padding(0.dp, 5.dp, 0.dp, 0.dp)
                        , tint = Farvekombi033
                    )

                    TextButton(onClick = { navController.navigate(Screen.ProfileScreen.route)}) {
                        Text(text = "Profil",
                            color = Color.Black,
                            fontSize = 18.sp,
                            modifier = Modifier.padding(10.dp, 0.dp, 0.dp, 0.dp))



                    }


                }
                Row(Modifier.padding(20.dp, 20.dp, 0.dp, 0.dp)) {
                    Icon(
                        painterResource(id = R.drawable.ic_baseline_group_add_24) ,
                        contentDescription = "",
                        modifier = Modifier
                            .size(45.dp, 45.dp)
                            .padding(0.dp, 5.dp, 0.dp, 0.dp), tint = Farvekombi033
                    )

                    TextButton(onClick = { /*TODO*/ }) {
                        Text(
                            text = "Inviter Venner",
                            color = Color.Black,
                            fontSize = 18.sp,
                            modifier = Modifier.padding(10.dp, 0.dp, 0.dp, 0.dp)
                        )
                    }
                }

                /*Row(Modifier.padding(20.dp, 20.dp, 0.dp, 0.dp)) {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = "",
                        modifier = Modifier
                                .size(45.dp, 45.dp)
                                .padding(0.dp, 5.dp, 0.dp, 0.dp), tint = Color.Cyan
                    )

                    TextButton(onClick = { /*TODO*/ }) {
                        Text(
                            text = "Indstillinger",
                            color = Color.Black

                            fontSize = 18.sp,
                            modifier = Modifier.padding(10.dp, 0.dp, 0.dp, 0.dp)
                        )
                    }
                }*/
                Row(Modifier.padding(20.dp, 20.dp, 0.dp, 0.dp)) {
                    Icon(
                        painterResource(id = R.drawable.ic_baseline_logout_24),
                        contentDescription = "",
                        modifier = Modifier
                            .size(45.dp, 45.dp)
                            .padding(0.dp, 5.dp, 0.dp, 0.dp)
                            .rotate(90f),
                        tint = Farvekombi033
                    )
                    TextButton(onClick = {
                        runBlocking {
                            frontpageViewModel.signOut()
                        }
                        if (FirebaseAuth.getInstance().currentUser == null) {
                            navController.navigate(Screen.LoginScreen.route)
                        }
                    }) {
                        Text(
                            text = "Sign out",
                            color = Color.Black,
                            fontSize = 18.sp,
                            modifier = Modifier.padding(10.dp, 0.dp, 0.dp, 0.dp)
                        )
                    }
                }
                /*Row(verticalAlignment = Bottom, horizontalArrangement = Arrangement.Center) {
                    Button(
                        onClick = {
                            scope.launch{
                                scaffoldstate.drawerState.close()}
                        },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = OplevFarve2,
                            contentColor = Color.White
                        ),
                        modifier = Modifier.size(240.dp, 40.dp),
                        shape = RoundedCornerShape(50)
                    ) {
                        Text(
                            text = "Luk", fontSize = 18.sp
                        )

                    }
                }*/
        },
        drawerGesturesEnabled = true,
        topBar = {
            var userName = ""
            runBlocking {
                userName = frontpageViewModel.getUserName(activity, context)
            }
            TopBar("Velkommen $userName", navController)
        },
        content = { paddingValues -> FrontPageColumn(frontpageViewModel.getCategories(), navController, frontpageViewModel, state) },
        bottomBar = { BottomBar(scope,scaffoldstate, frontpageViewModel) },
        floatingActionButtonPosition = FabPosition.Center,
        isFloatingActionButtonDocked = true,
        floatingActionButton = {
                Column() {
                    val alpha: Float by animateFloatAsState(if (state.fabExpanded) 315f else 0f)
                    var x : Float = 0f
                    var y : Float = 0f

                    if (state.fabExpanded) {
                        Row(modifier = Modifier
                                .graphicsLayer {
                                    translationX = 90f
                                    translationY = -180f}) {
                            FloatingActionButton(
                                onClick = { frontpageViewModel.changeDialogVal() },
                                modifier = Modifier.size(50.dp),
                                backgroundColor = Farvekombi033) {
                                Icon(
                                    imageVector = Icons.Filled.Add,
                                    contentDescription = "",
                                    modifier = Modifier
                                        .size(30.dp),
                                    tint = Color.White
                                )
                            }
                            Text(text = "Ny kategori")
                        }
                        Row(modifier = Modifier.graphicsLayer {
                                    translationX = 90f
                                    translationY = -160f
                                }) {
                            FloatingActionButton(onClick = {
                                navController.navigate(Screen.CreateJourneyScreen.route)
                            }, modifier = Modifier.size(50.dp), backgroundColor = Farvekombi033) {
                                Icon(
                                    imageVector = Icons.Filled.Add,
                                    contentDescription = "",

                                    modifier = Modifier
                                        .size(30.dp),
                                    tint = Color.White

                                )
                            }
                            Text(text = "Ny Rejse")
                        }

                        x = 60f
                        y = -140f
                    }

                    FloatingActionButton(shape = CircleShape,
                        modifier = Modifier
                            .size(width = 75.dp, height = 75.dp)
                            .graphicsLayer {
                                translationX = x
                                translationY = y
                            },
                        backgroundColor = Farvekombi033

                            ,
                        onClick = {
                            frontpageViewModel.expandFab()
                            //navController.navigate(Screen.CreateJourneyScreen.route)
                        }) {
                        Icon(
                            imageVector = Icons.Filled.Add,
                            contentDescription = "",
                            modifier = Modifier
                                .size(38.dp)
                                .rotate(alpha),
                            tint = Color.White
                        )
                    }
                }
        },
    )
}

@Composable
fun FrontPageColumn(categories: List<CategoryDto>, navController: NavController, frontPageViewModel: FrontPageViewModel, state: States) {
    Column(modifier = Modifier
        .verticalScroll(rememberScrollState())
        .fillMaxSize()
    )
    {
        val max = categories.size-1
        for (i in 0..max) {
            Spacer(modifier = Modifier
                .height(15.dp))
            CategoryRow(categories[i], navController = navController,frontPageViewModel)
        }
    }
    if(state.dialogState) {
        NewCategoryDialog(frontPageViewModel = frontPageViewModel)
    }
}

@Composable
fun NewCategoryDialog(frontPageViewModel: FrontPageViewModel){
    var categoryTitle by remember { mutableStateOf("") }
    val activity = LocalContext.current as Activity
    AlertDialog(
        onDismissRequest = { /*TODO*/ },
        title = { Text(text = "Indtast navnet på den nye kategori")},
        text = {
            OutlinedTextField(
                value = categoryTitle,
                label = { Text(text = "Kategori", textAlign = TextAlign.Center) },
                modifier = Modifier
                    .width(130.dp),
                onValueChange = {
                    categoryTitle = it
                },
                singleLine = true
            )
        },
        confirmButton = {
            Button(onClick = {
                runBlocking {
                    frontPageViewModel.createCategory(categoryTitle, activity)
                }
                frontPageViewModel.changeDialogVal()
            }) {
                Text(text = "Gem")
            }
        },
        dismissButton = {
            Button(onClick = {
                frontPageViewModel.changeDialogVal()
                frontPageViewModel.expandFab()
            }) {
                Text(text = "Annuller")
            }
        }
    )
}

@Composable
fun CategoryRow(category: CategoryDto, navController: NavController, frontPageViewModel: FrontPageViewModel) {
    val max = category.journeys.size-1
    Text(
        text = category.baseObject?.title.toString(),
        fontSize = 20.sp,
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        modifier = Modifier
            .padding(5.dp, 0.dp, 0.dp, 0.dp)
    )
    Row(modifier = Modifier
        .horizontalScroll(rememberScrollState()), verticalAlignment = Alignment.Bottom
    ) {
        for (i in 0..max) {
            JourneyCard(category.journeys[i], navController = navController,frontPageViewModel)
        }
        if (category.journeys.size < 1) {
                Text(text = "Ingen rejser oprettet", color = Color.LightGray, fontSize = 30.sp)
        }
    }
}

@Composable
fun JourneyCard(journey: Journey, navController: NavController,frontPageViewModel: FrontPageViewModel) {
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
            .clickable {
                val journeyId = journey.id
                navController.navigate(Screen.JourneyScreen.route + "/$journeyId")
            }
            /** Tror at nedstående skal ændres. Vi vil gerne have default paddings på hele projektet. */
            .padding(5.dp, 1.3.dp, 5.5.dp, 15.dp), elevation = 5.dp, backgroundColor = Color.LightGray) {

            Column() {
                Box(modifier = Modifier
                        .padding()
                        .fillMaxSize()) {
                    Image(
                        painter = painterResource(id = drawableId),
                        contentDescription = "Image Denmark",
                        contentScale = ContentScale.FillBounds
                    )
                    Box(
                        modifier = Modifier
                                //.fillMaxWidth().height(23.dp)
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
fun TopBar(title: String, navController: NavController) {
    val col = ("#E3C5A0").toColorInt()
    //E3C5A0
    TopAppBar( modifier = Modifier.height(65.dp),
        title = { Text(title,
        //modifier = Modifier.padding(60.dp, 0.dp, 0.dp, 0.dp)
        ) },
        navigationIcon = {
            IconButton(
                onClick = {
                    navController.navigate(Screen.FrontPageScreen.route)
                }
            )
            {
                Icon(
                    painter = painterResource(id = R.drawable.oplev_logo),
                    contentDescription = ""
                )
            }
                         },
                backgroundColor = Color(col)
            )
}

@Composable
fun BottomBar(scope: CoroutineScope, scaffoldState: ScaffoldState, frontPageViewModel: FrontPageViewModel){
    BottomAppBar(modifier = Modifier
        .height(65.dp),
        backgroundColor = Farvekombi032
    ) {
        BottomNavigation {
            BottomNavigationItem(
                icon = {
                    Icon(imageVector = Icons.Default.Menu, contentDescription = "" )},
                label = { Text(text = "Menu") },
                selected = false,
                onClick = { scope.launch{
                    scaffoldState.drawerState.open()
                } },
            modifier = Modifier.background(color= Farvekombi032))
            BottomNavigationItem(
                icon = {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        "")
                       },
                label = { Text(text = "Opdater") },
                selected = false,
                onClick = {
                    runBlocking {
                        frontPageViewModel.updateFrontPage()
                    }
                },
                modifier = Modifier.background(color= Farvekombi032))
        }
    }
}







