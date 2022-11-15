package com.example.oplev.sites.Idea

import android.view.Surface
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.oplev.Model.Idea
import com.example.oplev.ViewModel.IdeaViewModel
import com.example.oplev.ui.components.IdeaGrid
//import com.example.oplev.ui.components.TopBar

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