package com.example.oplev.sites

import android.app.Activity
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.oplev.data.dto.CategoryDto
import com.example.oplev.Model.Category
import com.example.oplev.Model.Journey
import com.example.oplev.R
import com.example.oplev.Screen
import com.example.oplev.ViewModel.FrontPageViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


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
                    IconButton(
                        onClick = { },
                        modifier = Modifier.padding(40.dp, 15.dp, 0.dp, 0.dp)
                    )
                    {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "")

                    }

                }
                Row(Modifier.padding(20.dp, 20.dp, 0.dp, 0.dp)) {
                    Icon(
                        imageVector = Icons.Default.AccountCircle,
                        contentDescription = "",
                        modifier = Modifier
                            .size(45.dp, 45.dp)
                            .padding(0.dp, 5.dp, 0.dp, 0.dp)
                        , tint = Color.Cyan
                    )

                    TextButton(onClick = { /*TODO*/ }) {
                        Text(text = "Profil",
                            fontSize = 18.sp,
                            modifier = Modifier.padding(10.dp, 0.dp, 0.dp, 0.dp))



                    }


                }
                Row(Modifier.padding(20.dp, 20.dp, 0.dp, 0.dp)) {
                    Icon(
                        imageVector = Icons.Default.Warning,
                        contentDescription = "",
                        modifier = Modifier
                            .size(45.dp, 45.dp)
                            .padding(0.dp, 5.dp, 0.dp, 0.dp), tint = Color.Cyan
                    )

                    TextButton(onClick = { /*TODO*/ }) {
                        Text(
                            text = "Inviter Venner",
                            fontSize = 18.sp,
                            modifier = Modifier.padding(10.dp, 0.dp, 0.dp, 0.dp)
                        )
                    }
                }

                Row(Modifier.padding(20.dp, 20.dp, 0.dp, 0.dp)) {
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
                            fontSize = 18.sp,
                            modifier = Modifier.padding(10.dp, 0.dp, 0.dp, 0.dp)
                        )
                    }
                }
                Row(Modifier.padding(20.dp, 20.dp, 0.dp, 0.dp)) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "",
                        modifier = Modifier
                            .size(45.dp, 45.dp)
                            .padding(0.dp, 5.dp, 0.dp, 0.dp)
                            .rotate(90f),
                        tint = Color.Cyan
                    )
                    TextButton(onClick = {

                        frontpageViewModel.signOut()

                        navController.navigate(Screen.LoginScreen.route)
                    }) {
                        Text(
                            text = "Sign out",
                            fontSize = 18.sp,
                            modifier = Modifier.padding(10.dp, 0.dp, 0.dp, 0.dp)
                        )
                    }
                }
                Row(verticalAlignment = Alignment.Bottom, modifier = Modifier.padding(55.dp, 200.dp, 0.dp, 0.dp)) {
                    Button(
                        onClick = {
                            scope.launch{
                                scaffoldstate.drawerState.close()}
                        },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color.Unspecified,
                            contentColor = Color.Black
                        ),
                        modifier = Modifier.size(240.dp, 40.dp),
                        shape = RoundedCornerShape(50)
                    ) {
                        Text(
                            text = "Luk", fontSize = 20.sp
                        )

                    }
                }
        },
        drawerGesturesEnabled = true,
        topBar = {
            var userName = ""
            runBlocking {
                userName = frontpageViewModel.getUserName(activity, context)
            }
            TopBar("Velkommen $userName")
        },
        content = { FrontPageColumn(frontpageViewModel.frontpageDto.categories, navController) },
        bottomBar = { BottomBar(scope,scaffoldstate) },
        floatingActionButtonPosition = FabPosition.Center,
        isFloatingActionButtonDocked = true,
        floatingActionButton = {
            FloatingActionButton(shape = CircleShape, modifier = Modifier.size(width = 75.dp, height = 75.dp),
                onClick = {
                    navController.navigate(Screen.CreateJourneyScreen.route)
                }) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "",
                    modifier = Modifier
                        .size(38.dp),
                    tint = Color.White
                )
            }
        },
    )
}

@Composable
fun FrontPageColumn(categories: List<CategoryDto>, navController: NavController) {
    Column(modifier = Modifier
        .verticalScroll(rememberScrollState())
    )
    {
        val max = categories.size-1
        for (i in 0..max) {
            Spacer(modifier = Modifier
                .height(15.dp))
            CategoryRow(categories[i], navController = navController)
        }
    }
}

@Composable
fun CategoryRow(category: CategoryDto, navController: NavController) {
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
            JourneyCard(category.journeys[i], navController = navController)
        }
    }
}

@Composable
fun JourneyCard(journey: Journey, navController: NavController) {
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
                navController.navigate(Screen.JourneyScreen.route)
            }
            /** Tror at nedstående skal ændres. Vi vil gerne have default paddings på hele projektet. */
            .padding(5.dp, 1.3.dp, 5.5.dp, 15.dp), elevation = 5.dp, backgroundColor = Color.LightGray) {

            Column() {
                Box(modifier = Modifier.padding().fillMaxSize()) {
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
fun TopBar(title: String) {
    TopAppBar( modifier = Modifier.height(65.dp),
        title = { Text(title,
        //modifier = Modifier.padding(60.dp, 0.dp, 0.dp, 0.dp)
        ) },
        navigationIcon = {
            IconButton(
                onClick = { TODO() }
            )
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
fun BottomBar(scope: CoroutineScope, scaffoldState: ScaffoldState){
    BottomAppBar(modifier = Modifier
        .height(65.dp) /*cutoutShape = CircleShape,*/) {
        BottomNavigation {
            BottomNavigationItem(
                icon = {
                    IconButton(
                        onClick = {scope.launch{
                            scaffoldState.drawerState.open()
                        }}
                    ) {
                        Icon(imageVector = Icons.Default.Menu, contentDescription = "" )}
                       },
                label = { Text(text = "Menu") },
                selected = false,
                onClick = { })
            BottomNavigationItem(
                icon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        "")
                       },
                label = { Text(text = "Søg") },
                selected = false,
                onClick = {})
        }
    }
}







