package com.example.oplev.sites.Journy

import JourneyUiState
import JourneyViewModel
import android.app.Activity
import android.media.Image
import android.service.autofill.OnClickAction
import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.example.oplev.Model.Idea
import com.example.oplev.Model.Folder
import com.example.oplev.Model.States
import com.example.oplev.Screen
import com.example.oplev.ViewModel.CreateIdeaViewModel
import com.example.oplev.ViewModel.FrontPageViewModel
import com.example.oplev.sites.*

import com.example.oplev.data.dataService.IdeaDataService
import com.example.oplev.data.dataService.QueueDataService
import com.example.oplev.data.dto.CategoryDto
import com.example.oplev.data.roomDao.IdeaDao
import com.example.oplev.ui.theme.Farvekombi031
import com.example.oplev.ui.theme.Farvekombi032
import com.example.oplev.ui.theme.Farvekombi033
import com.example.oplev.ui.theme.OplevFarve2
import compose.icons.LineAwesomeIcons
import compose.icons.lineawesomeicons.ArrowAltCircleLeft
import compose.icons.lineawesomeicons.PlusSolid
import kotlinx.coroutines.runBlocking

// make an alias
typealias ComposableFun = @Composable () -> Unit


@Composable
fun JourneyScreen(journeyViewModel: JourneyViewModel, navController: NavController) {
    val context = LocalContext.current
    val activity = LocalContext.current as Activity
    val uiState by journeyViewModel.uiState.collectAsState()
    val state = journeyViewModel.stateFolder.value

    Scaffold(
        topBar = {
            var journeyName = ""
            runBlocking {
                journeyName = journeyViewModel.getJourneyTitle()
            }
            TopBar( "$journeyName", navController)},
        content = {
            Column(modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                //.verticalScroll(rememberScrollState())
            ) {
                if(state.dialogState) {
                    NewCategoryDialog(journeyViewModel, uiState)
                }
                imageAndText(text = journeyViewModel.getJourneyTitle(), image = null, journeyViewModel = journeyViewModel, navController = navController)
                gridForFoldersAndIdeas(uiState.folders, uiState.ideas,
                    onFolderClick = {it -> journeyViewModel.openNewFolder(it)},
                    onIdeaClick = {it -> navController.navigate(Screen.IdeaScreen.route + "/$it")})
            }
                  },
        bottomBar = { BottomBar(viewModel = journeyViewModel, navController = navController)},
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
                            onClick = { journeyViewModel.changeDialogVal() },
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
                        Text(text = "Ny mappe")
                    }
                    Row(modifier = Modifier.graphicsLayer {
                        translationX = 90f
                        translationY = -160f
                    }) {
                        FloatingActionButton(onClick = {
                            navController.navigate(Screen.CreateIdeaScreen.route+"/${uiState.openFolder!!.id}")
                        }, modifier = Modifier.size(50.dp),
                        backgroundColor = Farvekombi033) {
                            Icon(
                                imageVector = Icons.Filled.Add,
                                contentDescription = "",
                                modifier = Modifier
                                    .size(30.dp),
                                tint = Color.White
                            )
                        }
                        Text(text = "Ny ide")
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
                        }
                    ,
                    onClick = {
                        journeyViewModel.expandFab()
                    },
                backgroundColor = Farvekombi033) {
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
fun NewCategoryDialog(journeyViewModel: JourneyViewModel, uiState: JourneyUiState){
    var folderTitle by remember { mutableStateOf("") }
    val activity = LocalContext.current as Activity
    AlertDialog(
        onDismissRequest = { /*TODO*/ },
        title = { Text(text = "Indtast navnet på den nye mappe")},
        text = {
            OutlinedTextField(
                value = folderTitle,
                label = { Text(text = "mappe", textAlign = TextAlign.Center) },
                modifier = Modifier
                    .width(130.dp),
                onValueChange = {
                    folderTitle = it
                },
                singleLine = true
            )
        },
        confirmButton = {
            Button(onClick = {
                runBlocking {
                    journeyViewModel.createFolder(folderTitle, uiState.openFolder!!.journeyId ,uiState.openFolder!!.id ,activity)
                }
                journeyViewModel.changeDialogVal()
            }) {
                Text(text = "Gem")
            }
        },
        dismissButton = {
            Button(onClick = {
                journeyViewModel.changeDialogVal()
                journeyViewModel.expandFab()
            }) {
                Text(text = "Annuller")
            }
        }
    )
}


@Composable
fun imageAndText(
    text: String,
    image: Image?,
    journeyViewModel: JourneyViewModel,
navController: NavController) {
    Box(modifier = Modifier.height(150.dp)){
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
                .fillMaxSize(),

            contentScale = ContentScale.Crop
        )
         }
        Divider(startIndent = 0.dp, thickness = 5.dp, color = Farvekombi031, modifier = Modifier.align(
            BottomCenter))
        }
    }


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun gridForFoldersAndIdeas(folders: List<Folder>, ideas: List<Idea>, onFolderClick:(Folder)-> Unit, onIdeaClick:(String)-> Unit){

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
fun ideaCreator(idea: Idea, onIdeaClick:(String)-> Unit){
    Column(
        modifier = Modifier.size(100.dp)
    ){
        IconButton(onClick = {onIdeaClick(idea.id)}, Modifier.align(Alignment.CenterHorizontally)) {
            Icon(painter = painterResource(id = R.drawable.ic_baseline_lightbulb_24),
                contentDescription = "Idea",
                modifier = Modifier.size(80.dp))
        }
        Text(text = idea.title, modifier = Modifier.align(Alignment.CenterHorizontally))
    }
}

@Composable
fun BottomBar(viewModel: JourneyViewModel, navController: NavController){
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
                onClick = {if(viewModel.checkIfPopIsNull()){
                    navController.navigate("frontpage")
                } else viewModel.goBackFromFolder()},
                modifier = Modifier.background(color= Farvekombi032))
                        }
    }
}























@Preview(showBackground = true)
@Composable
fun JourneyPreview(){
    var testJourney = Journey(tag = "test", image = null, date = null, description = "This is a test", title = "Danmark", id = "asd", categoryID = "1")
   // JourneyScreen(journeyViewModel = JourneyViewModel(testJourney))
}