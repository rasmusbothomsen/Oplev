package com.example.oplev.sites.Idea

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalOf
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.oplev.R
import com.example.oplev.ViewModel.IdeaViewModel
import com.example.oplev.sites.TopBar
import com.example.oplev.ui.theme.Farvekombi031
import com.example.oplev.ui.theme.Farvekombi032
import com.example.oplev.ui.theme.OplevBlue
import compose.icons.LineAwesomeIcons
import compose.icons.lineawesomeicons.ArrowAltCircleLeft
import compose.icons.lineawesomeicons.Lightbulb
import compose.icons.lineawesomeicons.LinkSolid


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun IdeaScreen(ideaViewModel: IdeaViewModel, navController: NavController) {
     val context = LocalContext.current
     val activity = LocalContext.current as Activity
     val uiState by ideaViewModel.uiState.collectAsState()


    Scaffold(
        topBar = {
            TopBar(title = ideaViewModel.currentIdea.title, navController)
        },
        content = {
            Column(Modifier.verticalScroll(rememberScrollState())) {
                Image(
                    // Placeholder image - skal ændres til image fra databasen
                    painter = painterResource(id = R.drawable.img_denmark),
                    contentDescription = "Idea image",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                )

                // IDEA NAME

                Box(
                    modifier = Modifier
                        .background(Color.White)
                        .fillMaxSize()
                ) {
                    Column(
                        modifier = Modifier
                            .padding(10.dp)
                    ) {
                        Spacer(modifier = Modifier.height(15.dp))
                        Row {
                            Icon(
                                imageVector = LineAwesomeIcons.Lightbulb,
                                contentDescription = "",
                                modifier = Modifier.padding(10.dp, 10.dp, 10.dp, 10.dp)
                            )
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(
                                        50
                                            .dp
                                    )
                                    .clip(RoundedCornerShape(30.dp))
                                    .background(Color.LightGray, RoundedCornerShape(30.dp))
                                    .padding(10.dp),
                            ) {
                                Text(
                                    text = ideaViewModel.currentIdea.title,
                                    fontSize = 15.sp,
                                    modifier = Modifier.padding(5.dp)
                                )
                            }
                        }

                    }
                }

                // IDEA DESCRIPTION

                Box(
                    modifier = Modifier
                        .background(Color.White)
                        .fillMaxSize()
                ) {
                    Column(
                        modifier = Modifier
                            .padding(10.dp)
                    ) {
                        Spacer(modifier = Modifier.height(15.dp))
                        Row {
                            Icon(
                                imageVector = Icons.Filled.Info,
                                contentDescription = "",
                                modifier = Modifier.padding(10.dp, 10.dp, 10.dp, 10.dp)
                            )
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(150.dp)
                                    .clip(RoundedCornerShape(30.dp))
                                    .background(Color.LightGray, RoundedCornerShape(30.dp))
                                    .padding(10.dp),
                            ) {
                                Text(
                                    text = ideaViewModel.currentIdea.description,
                                    fontSize = 15.sp,
                                    modifier = Modifier.padding(5.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(15.dp))

                        // IDEA DATE

                        Row {
                            Icon(
                                imageVector = Icons.Filled.DateRange,
                                contentDescription = "",
                                modifier = Modifier.padding(10.dp, 10.dp, 10.dp, 10.dp)
                            )
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(50.dp)
                                    .clip(RoundedCornerShape(30.dp))
                                    .background(Color.LightGray, RoundedCornerShape(30.dp))
                                    .padding(10.dp)
                            ) {
                                Text(
                                    text = ideaViewModel.currentIdea.date,
                                    fontSize = 15.sp,
                                    modifier = Modifier.padding(5.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(15.dp))

                        /* IDEA LOCATION

                        Row {
                            Icon(
                                imageVector = Icons.Filled.LocationOn,
                                contentDescription = "",
                                modifier = Modifier.padding(10.dp, 10.dp, 10.dp, 10.dp)
                            )
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(50.dp)
                                    .clip(RoundedCornerShape(30.dp))
                                    .background(Color.LightGray, RoundedCornerShape(30.dp))
                                    .padding(10.dp)
                            )

                        }
                        Spacer(modifier = Modifier.height(100.dp))


                         */
                        // IDEA LINK

                        Row(
                            modifier = Modifier
                                .padding(40.dp)
                                .fillMaxWidth()
                        ) {
                            val context = LocalContext.current
                            val intent = remember {
                                Intent(
                                    Intent.ACTION_VIEW,
                                    Uri.parse(ideaViewModel.currentIdea.link)
                                )
                            }
                            Button(
                                onClick = {
                                    context.startActivity(intent)
                                },
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = Farvekombi031,
                                    contentColor = Color.Black
                                ),
                                modifier = Modifier
                                    .size(130.dp, 40.dp)
                                    .fillMaxWidth()
                                    .height(50.dp),
                                shape = RoundedCornerShape(50.dp)
                            ) {
                                Icon(
                                    imageVector = LineAwesomeIcons.LinkSolid,
                                    contentDescription = "",
                                )
                                Text(text = "Link", fontSize = 18.sp)
                            }
                            Spacer(modifier = Modifier.width(30.dp))

                            Button(
                                onClick = { /*TODO*/ },
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = Farvekombi032,
                                    contentColor = Color.Black
                                ),
                                modifier = Modifier
                                    .size(130.dp, 40.dp)
                                    .fillMaxWidth()
                                    .height(50.dp),

                                shape = CircleShape
                            ) {
                                Text(text = "Edit", fontSize = 18.sp)
                            }
                        }


                    }

                }
            }


        },
        bottomBar = { BottomBar() }
    )
}

@Composable
fun BottomBar(){
    BottomAppBar(modifier = Modifier.height(65.dp),
        backgroundColor = Farvekombi032) {
        BottomNavigation() {
            BottomNavigationItem(
                icon = { Icon(imageVector = Icons.Default.Menu, "") },
                label = { Text(text = "Menu") },
                selected = false,
                onClick = {},
                modifier = Modifier.background(color= Farvekombi032))
            BottomNavigationItem(
                icon = { Icon(LineAwesomeIcons.ArrowAltCircleLeft, "") },
                label = { Text(text = "Tilbage") },
                selected = false,
                onClick = {},
                modifier = Modifier.background(color= Farvekombi032))
        }
    }
}