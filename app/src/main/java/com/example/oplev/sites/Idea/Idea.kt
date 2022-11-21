package com.example.oplev.sites.Idea

import android.media.Image
import android.view.Surface
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.oplev.Model.Category
import com.example.oplev.Model.Idea
import com.example.oplev.R
import com.example.oplev.ViewModel.IdeaViewModel
import com.example.oplev.sites.Journy.BottomBar
import com.example.oplev.sites.Journy.TopBar
import com.example.oplev.sites.Journy.ideas
import com.example.oplev.ui.theme.OplevBlue
import com.example.oplev.ui.theme.OplevMain
import com.example.oplev.ui.theme.Oplevsec

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
 */

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


// Undersøg om en box kan være clickable eller lav box om til button.
// Få indkorporeret items på journeypageLazyGrid
// rangeret fol
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

@Preview
@Composable
fun IdeaGriddyPreview() {
    IdeaGriddy()
}
