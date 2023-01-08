package com.example.oplev.sites.Idea

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.oplev.R
import com.example.oplev.ViewModel.IdeaViewModel
import com.example.oplev.sites.Journy.BottomBar
import com.example.oplev.sites.Journy.TopBar
import com.example.oplev.ui.theme.OplevBlue
import compose.icons.LineAwesomeIcons
import compose.icons.lineawesomeicons.Lightbulb

//import com.example.oplev.ui.components.IdeaGrid

//import com.example.oplev.ui.components.TopBar

/*
@Composable
fun IdeaScreen(ideaViewModel: IdeaViewModel, modifier: Modifier = Modifier) {
    val scaffoldState = rememberScaffoldState()
    Scaffold (
        //topBar = { *Indsæt TopBar },
        //bottomBar = { *Indsæt BottomBar },
        //Evt. drawer = { *Indsæt Drawer* },

        scaffoldState = scaffoldState,
        content = { Text("test")}
            )
    }


@Preview (showBackground = true)
@Composable
fun IdeaScreenPreview(){
    val IdeaViewModel = IdeaViewModel(Idea("Idea1", "My description 1", null,null,))
    IdeaScreen(ideaViewModel = IdeaViewModel)
    IdeaGrid()
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun IdeaGriddy() {
    Scaffold(
        topBar = { TopBar(title = "{journeyName} Ideas") }
    ) {
        LazyVerticalGrid(
            cells = GridCells.Fixed(3),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(12.dp),
        )
        {
            items(10) { idea ->
                IdeaGridItem(viewModel = IdeaViewModel())
            }
        }

    }

}


 */

// Undersøg om en box kan være clickable eller lav box om til button.
// Få indkorporeret items på journeypageLazyGrid
// find ud af hvordan folders og ideas skal vises
@Composable
fun IdeaGridItem(viewModel: IdeaViewModel) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .aspectRatio(1f)
            .clip(RoundedCornerShape(30.dp)),
        contentAlignment = Alignment.BottomCenter
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_background),
            contentDescription = "Idea Image",
            modifier = Modifier
                .fillMaxSize()
                .aspectRatio(1f)
                .clip(RoundedCornerShape(30.dp))
        )

    }
}

@Composable
fun IdeaScreen() {
    Scaffold(
        topBar = { TopBar(title = "{journeyName}") },
        content = {
            Column() {
                Image(
                    painter = painterResource(id = R.drawable.img_denmark),
                    contentDescription = "Idea name",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                )
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
                                    .padding(10.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(15.dp))
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
                            )

                        }

                        Spacer(modifier = Modifier.height(15.dp))

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

                        Row (
                            modifier = Modifier
                                .padding(40.dp)
                                .fillMaxWidth()
                        ){
                            Button(
                                onClick = { /* TODO */ },
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = Color.Yellow,
                                    contentColor = Color.Black
                                ),
                                modifier = Modifier
                                    .size(130.dp, 40.dp),
                                shape = RoundedCornerShape(50.dp)
                            ) {
                                Icon(
                                    imageVector = LineAwesomeIcons.Lightbulb,
                                    contentDescription = "",
                                )
                                Text(text = "Link", fontSize = 18.sp)
                            }
                            Spacer(modifier = Modifier.width(30.dp))
                            Button(
                                onClick = { /* TODO */ },
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = OplevBlue,
                                    contentColor = Color.Black
                                ),
                                modifier = Modifier
                                    .size(130.dp, 40.dp),
                                shape = RoundedCornerShape(50.dp)
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




@Preview
@Composable
fun IdeaScreenPreview() {
    IdeaScreen()
}
