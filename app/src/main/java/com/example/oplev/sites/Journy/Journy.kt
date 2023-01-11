package com.example.oplev.sites.Journy

import android.app.Activity
import android.media.Image
import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.oplev.Model.Journey
import com.example.oplev.R
import androidx.compose.material.Text
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.runtime.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.example.oplev.Model.Idea
import com.example.oplev.Model.Folder
import com.example.oplev.Screen
import com.example.oplev.sites.*

import com.example.oplev.ViewModel.JourneyViewModel
import com.example.oplev.ui.theme.OplevFarve2
import kotlinx.coroutines.runBlocking

// make an alias
typealias ComposableFun = @Composable () -> Unit


@Composable
fun JourneyScreen(journeyViewModel: JourneyViewModel, navController: NavController) {
    val context = LocalContext.current
    val activity = LocalContext.current as Activity
    val uiState by journeyViewModel.uiState.collectAsState()


    Scaffold(
        topBar = {
            var userName = ""
            runBlocking {
                userName = journeyViewModel.getUserName(activity, context)
            }
            TopBar("Velkommen $userName")},
        content = {
            Column(modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
            ) {
                imageAndText(text = journeyViewModel.getJourneyTitle(), image = null, journeyViewModel = journeyViewModel)
                gridForFoldersAndIdeas(uiState.folders, uiState.ideas,
                    onFolderClick = {it -> journeyViewModel.openNewFolder(it)},
                    onIdeaClick = {it -> /** nav controller**/})
            }
                  },
        bottomBar = { BottomBar()} )
}


@Composable
fun imageAndText(
    text: String,
    image: Image?,
    journeyViewModel: JourneyViewModel) {
    Box(modifier = Modifier.height(180.dp)){
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
    )
    {
        Image(
            painter = painterResource(id = R.drawable.img_denmark),
            contentDescription = "Placerholder image",
            modifier = Modifier
                .height(150.dp)
                .fillMaxWidth(),
            contentScale = ContentScale.Crop
        )
        Box(
            modifier = Modifier
                .align(alignment = Alignment.CenterHorizontally)
                .background(OplevFarve2)
                .fillMaxWidth()
        ) {
            Text(
                text = journeyViewModel.getJourneyTitle(),
                fontSize = 17.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
         }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun gridForFoldersAndIdeas(folders: List<Folder>, ideas: List<Idea>, onFolderClick:(Folder)-> Unit, onIdeaClick:(Idea)-> Unit){

    LazyVerticalGrid(cells = GridCells.Fixed(3),horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(24.dp)){

        if(folders != null){
            folders.forEachIndexed {
                index, function -> item { folderCreator(function, onFolderClick) }
            }
        }
        if(ideas != null){
            ideas.forEachIndexed {
                index, function -> item { ideaCreator(function, onIdeaClick) }
            }
        }
    }

}

@Composable
fun folderCreator(folder: Folder, onFolderClick:(Folder)-> Unit){
    Column(
        modifier = Modifier.size(100.dp)
    ) {
        IconButton(onClick = {onFolderClick(folder)}, Modifier.align(Alignment.CenterHorizontally)) {
            Icon(painter = painterResource(id = R.drawable.ic_baseline_folder_24),
                contentDescription = "Folder",
                modifier = Modifier.size(80.dp))
        }
        Text(text = folder.title, modifier = Modifier.align(Alignment.CenterHorizontally))
    }
}

@Composable
fun ideaCreator(idea: Idea, onIdeaClick:(Idea)-> Unit){
    Column(
        modifier = Modifier.size(100.dp)
    ){
        IconButton(onClick = {onIdeaClick(idea)}, Modifier.align(Alignment.CenterHorizontally)) {
            Icon(painter = painterResource(id = R.drawable.ic_baseline_lightbulb_24),
                contentDescription = "Idea",
                modifier = Modifier.size(80.dp))
        }
        Text(text = idea.title, modifier = Modifier.align(Alignment.CenterHorizontally))
    }
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






















/*

@Composable
fun folderCreator2(ideas: List<Idea>?){
    Box(modifier = Modifier
        .size(100.dp)
        .clip(RoundedCornerShape(20.dp))
        .background(Color.LightGray)){
        Column(modifier = Modifier
            .fillMaxSize()
            .align(Alignment.Center)){
            Row(modifier = Modifier.align(Alignment.CenterHorizontally)){
                Image(
                    painter = painterResource(id = R.drawable.img_denmark),
                    modifier = Modifier
                        .size(50.dp)
                        .padding(5.dp)
                        .clip(RoundedCornerShape(10.dp)),
                    alignment = Alignment.TopStart,
                    contentDescription = "",
                    contentScale = ContentScale.Crop
                )
                Image(
                    painter = painterResource(id = R.drawable.img_denmark),
                    modifier = Modifier
                        .size(50.dp)
                        .padding(5.dp)
                        .clip(RoundedCornerShape(10.dp)),
                    alignment = Alignment.TopEnd,
                    contentDescription = "",
                    contentScale = ContentScale.Crop
                )
            }
            Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                Image(
                    painter = painterResource(id = R.drawable.img_denmark),
                    modifier = Modifier
                        .size(50.dp)
                        .padding(5.dp)
                        .clip(RoundedCornerShape(10.dp)),
                    alignment = Alignment.BottomStart,
                    contentDescription = "",
                    contentScale = ContentScale.Crop
                )
                Image(
                    painter = painterResource(id = R.drawable.img_denmark),
                    modifier = Modifier
                        .size(50.dp)
                        .padding(5.dp)
                        .clip(RoundedCornerShape(10.dp)),
                    alignment = Alignment.BottomEnd,
                    contentDescription = "",
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}


*/

@Preview(showBackground = true)
@Composable
fun JourneyPreview(){
    var testJourney = Journey(tag = "test", image = null, date = null, description = "This is a test", title = "Danmark", id = "asd", categoryID = "1")
   // JourneyScreen(journeyViewModel = JourneyViewModel(testJourney))
}