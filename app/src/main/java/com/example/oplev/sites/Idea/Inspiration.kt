package com.example.oplev.sites.Idea

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.oplev.sites.TopBar
import compose.icons.LineAwesomeIcons
import compose.icons.lineawesomeicons.Image
import compose.icons.lineawesomeicons.LinkSolid
import compose.icons.lineawesomeicons.PenSolid
import com.example.oplev.R
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.example.oplev.ViewModel.IdeaViewModel
import com.example.oplev.sites.Journy.ComposableFun
import com.example.oplev.sites.Journy.folderCreator




@Composable
fun InspirationBottomBar(){
    BottomAppBar(modifier = Modifier.height(65.dp), cutoutShape = CircleShape) {
        BottomNavigation() {
            BottomNavigationItem(
                icon = { Icon(imageVector = LineAwesomeIcons.PenSolid, "") },
                label = { Text(text = "Noter") },
                selected = false,
                onClick = {  })
            BottomNavigationItem(
                icon = { Icon(imageVector = LineAwesomeIcons.LinkSolid, "") },
                label = { Text(text = "Links") },
                selected = false,
                onClick = {})
            BottomNavigationItem(
                icon = { Icon(imageVector = LineAwesomeIcons.Image, "") },
                label = { Text(text = "Billeder") },
                selected = false,
                onClick = {})
        }
    }
}



@Composable
fun InspirationScreen(navController: NavController) {
    Scaffold(
        topBar = { TopBar(title = "Inspirationer", navController) },
        content = {

        },
        bottomBar = { InspirationBottomBar() } )
}

@Composable
fun NotesScreen(navController: NavController) {
    Scaffold(
        topBar = { TopBar(title = "Noter", navController) },
        content = {
            Column(
                modifier = Modifier
                    .padding(5.dp)
            ) {
                inputTextfield(label = "", height = 700, imageVector = LineAwesomeIcons.PenSolid, onValueChange = {}, input = "")

            }
        },
        bottomBar = { InspirationBottomBar() } )
}

@Composable
fun LinksScreen(navController: NavController) {
    Scaffold(
        topBar = { TopBar(title = "Links", navController) },
        content = {


        },
        bottomBar = { InspirationBottomBar() } )
}

@Composable
fun PictureItem() {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .aspectRatio(1f)
            .clip(RoundedCornerShape(10.dp)),
        contentAlignment = Alignment.BottomCenter
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_background),
            contentDescription = "Picture",
            modifier = Modifier
                .fillMaxSize()
                .aspectRatio(1f)
                .clip(RoundedCornerShape(10.dp))
        )

    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PictureScreen(navController: NavController) {
    Scaffold(
        topBar = { TopBar(title = "Billeder", navController) },
        content = {
            val test: ComposableFun = {
                Box(modifier = Modifier
                    .size(100.dp)
                    .background(Color.Black)) {
                    Button(onClick = { /*Opens image */}) {

                    }

                }
            }

            LazyVerticalGrid(GridCells.Fixed(3),horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(24.dp)
            ){

            }

        },
        bottomBar = { InspirationBottomBar() } )

}



@Preview
@Composable
fun InspirationScreenPreview() {
}