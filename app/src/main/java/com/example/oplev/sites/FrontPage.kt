package com.example.oplev.sites

import android.app.Activity
import android.content.Context
import android.net.Uri
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
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
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.fontResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.graphics.toColorInt
import androidx.navigation.NavController
import com.example.oplev.MainActivity
import com.example.oplev.Model.Category
import com.example.oplev.data.dto.CategoryDto
import com.example.oplev.Model.Journey
import com.example.oplev.Model.States
import com.example.oplev.R
import com.example.oplev.Screen
import com.example.oplev.ViewModel.FrontPageViewModel
import com.example.oplev.ui.theme.*
import com.google.firebase.auth.FirebaseAuth
import com.example.oplev.ui.theme.OplevFarve2
import com.example.oplev.ui.theme.Farvekombi033
import com.example.oplev.ui.theme.OplevBlue
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.google.android.exoplayer2.ui.StyledPlayerView
import kotlinx.coroutines.*


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
    var categories: List<CategoryDto>

    Box {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(R.drawable.background),
            contentDescription = "background",
            contentScale = ContentScale.FillBounds
        )
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
                            .padding(0.dp, 5.dp, 0.dp, 0.dp), tint = Farvekombi033
                    )

                    TextButton(onClick = { navController.navigate(Screen.ProfileScreen.route) }) {
                        Text(
                            text = "Profil",
                            color = Color.Black,
                            fontSize = 18.sp,
                            modifier = Modifier.padding(10.dp, 0.dp, 0.dp, 0.dp)
                        )


                    }


                }
                Row(Modifier.padding(20.dp, 20.dp, 0.dp, 0.dp)) {
                    Icon(
                        painterResource(id = R.drawable.ic_baseline_group_add_24),
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
                        frontpageViewModel.logOutdialog()
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
            content = { paddingValues ->
                runBlocking {
                    categories = frontpageViewModel.getCategories()
                }
                FrontPageColumn(
                    categories, navController, frontpageViewModel, state
                )
            },
            bottomBar = {
                var context = LocalContext.current
                var pageIsUpdated = state.frontPageUpdated

                BottomAppBar(
                    modifier = Modifier
                        .height(65.dp),
                    backgroundColor = Farvekombi032
                ) {
                    BottomNavigation {
                        BottomNavigationItem(
                            icon = {
                                Icon(imageVector = Icons.Default.Menu, contentDescription = "")
                            },
                            label = { Text(text = "Menu") },
                            selected = false,
                            onClick = {
                                scope.launch {
                                    scaffoldstate.drawerState.open()
                                }
                            },
                            modifier = Modifier.background(color = Farvekombi032)
                        )
                        BottomNavigationItem(
                            icon = {
                                Icon(
                                    imageVector = Icons.Default.Refresh,
                                    ""
                                )
                            },
                            label = { Text(text = "Opdater") },
                            selected = false,
                            onClick = {
                                runBlocking {
                                    categories = frontpageViewModel.getCategories()
                                    frontpageViewModel.updateFrontPage()
                                }
                                if (pageIsUpdated) {
                                    Toast.makeText(context, "Rejser opdateret!", Toast.LENGTH_SHORT)
                                        .show()
                                    frontpageViewModel.changeupdatedStat()
                                }
                            },
                            modifier = Modifier.background(color = Farvekombi032)
                        )
                    }
                }

            },
            floatingActionButtonPosition = FabPosition.Center,
            isFloatingActionButtonDocked = true,
            floatingActionButton = {
                Column() {
                    val alpha: Float by animateFloatAsState(if (state.fabExpanded) 315f else 0f)
                    var x: Float = 0f
                    var y: Float = 0f

                    if (state.fabExpanded) {
                        Row(modifier = Modifier
                            .graphicsLayer {
                                translationX = 90f
                                translationY = -180f
                            }) {
                            FloatingActionButton(
                                onClick = {
                                    frontpageViewModel.changeDialogVal()
                                    blurred.value = 16.dp
                                    frontpageViewModel.expandFab()
                                },
                                modifier = Modifier.size(50.dp),
                                backgroundColor = Farvekombi033
                            ) {
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
                        backgroundColor = Farvekombi033,
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
            backgroundColor = Color.Transparent,
        )
    }
}


    var blurred = mutableStateOf(0.dp)

@Composable
fun FrontPageColumn(categories: List<CategoryDto>, navController: NavController, frontPageViewModel: FrontPageViewModel, state: States) {

        var refreshing by remember { mutableStateOf(false) }
        LaunchedEffect(refreshing) {
            if (refreshing) {
                delay(3000)
                refreshing = false
            }
        }

        SwipeRefresh(
            state = rememberSwipeRefreshState(isRefreshing = refreshing),
            onRefresh = {
                runBlocking {
                    frontPageViewModel.updateFrontPage()
                }
                refreshing = true },
        ) {

        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
                .blur(blurred.value),

        )
        {
            val max = categories.size - 1
            for (i in 0..max) {
                Spacer(
                    modifier = Modifier
                        .height(15.dp)
                )
                CategoryRow(categories[i], navController = navController, frontPageViewModel)
            }
        }
        if (state.dialogState) {
            NewCategoryDialog(frontPageViewModel = frontPageViewModel)
        }
            if (state.logOutdialog) {
                SignOutDialog(frontPageViewModel = frontPageViewModel, navController)
            }
    }
}


/*
   runBlocking {
                            frontpageViewModel.signOut()
                        }
                        if (FirebaseAuth.getInstance().currentUser == null) {
                            navController.navigate(Screen.LoginScreen.route)
                        }

 */

@Composable
fun SignOutDialog(frontPageViewModel: FrontPageViewModel, navController: NavController){
    AlertDialog(
        onDismissRequest = { /*TODO*/ },
        title = { Text(text = "LOG AF")},
        text = {
          Text(text = "Er du sikker på, at du vil logge af?")
        },
        confirmButton = {
            Button(onClick = {
                runBlocking {
                    frontPageViewModel.signOut()
                }
                if (FirebaseAuth.getInstance().currentUser == null) {
                    navController.navigate(Screen.LoginScreen.route)
                }
                frontPageViewModel.logOutdialog()
            }) {
                Text(text = "JA")
            }
        },
        dismissButton = {
            Button(onClick = {
                frontPageViewModel.logOutdialog()
            }) {
                Text(text = "NEJ")
            }
        }
    )
}

@Composable
fun NewCategoryDialog(frontPageViewModel: FrontPageViewModel){
    var categoryTitle by remember { mutableStateOf("") }
    val activity = LocalContext.current as Activity
    AlertDialog(
        onDismissRequest = { /*TODO*/ },
        title = { Text(text = "Indtast navnet på den nye kategori")},
        text = {
            Text(text = "")
            OutlinedTextField(
                value = categoryTitle,
                label = { Text(text = "Kategori", textAlign = TextAlign.Center) },
                modifier = Modifier
                    .fillMaxWidth(),
                onValueChange = {
                    categoryTitle = it
                },
                singleLine = true,
                shape = CircleShape
            )
        },
        shape = RoundedCornerShape(25.dp),
        confirmButton = {
            Button(onClick = {
                runBlocking {
                    frontPageViewModel.createCategory(categoryTitle, activity)
                }
                frontPageViewModel.changeDialogVal()
                blurred.value = 0.dp
            },
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Farvekombi032,
                    contentColor = Color.White
                )
            ) {
                Text(text = "Gem")
            }
        },
        dismissButton = {
            TextButton(onClick = {
                frontPageViewModel.changeDialogVal()
                frontPageViewModel.expandFab()
                blurred.value = 0.dp
            }) {
                Text(text = "Annuller", color = Color.Black)
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
        fontFamily = fontsmedium,
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
                Text(text = "Ingen rejser oprettet", color = Color.LightGray, fontSize = 18.sp, modifier = Modifier.padding(5.dp, 0.dp, 0.dp, 35.dp))
        }
    }
}

@Composable
fun JourneyCard(journey: Journey, navController: NavController,frontPageViewModel: FrontPageViewModel) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val drawableId = remember("palmtree") {
        context.resources.getIdentifier(
            "palmtree",
            "drawable",
            context.packageName
        )
    }
   val painter = mutableStateOf(painterResource(id = drawableId))
    coroutineScope.launch {
    val bitMappainter = (frontPageViewModel.getImage(500,300,journey.imageId!!))
    if(bitMappainter !=null){
    painter.value = BitmapPainter(bitMappainter.asImageBitmap())
    }
    }

    Card(modifier = Modifier
        .clickable {
            val journeyId = journey.id
            navController.navigate(Screen.JourneyScreen.route + "/$journeyId")
        }
        /** Tror at nedstående skal ændres. Vi vil gerne have default paddings på hele projektet. */
        .padding(5.dp, 1.3.dp, 5.5.dp, 15.dp), elevation = 5.dp, backgroundColor = Farvekombi033) {
            Column() {
                Box(modifier = Modifier
                    .padding()
                    .fillMaxSize()) {
                    Image(
                        painter = painter.value,
                        contentDescription = "Image Denmark",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.aspectRatio(painter.value.intrinsicSize.width / painter.value.intrinsicSize.height).fillMaxWidth()
                    )
                    Box(
                        modifier = Modifier
                            .width(185.dp)
                            .align(Alignment.BottomCenter)
                            .background(Color.Black.copy(alpha = 0.3f))
                    ) {
                        Text(
                            fontSize = 16.sp,
                            text = journey.title,
                            modifier = Modifier
                                .align(Alignment.Center)
                                .fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            fontFamily = fonts1,
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







