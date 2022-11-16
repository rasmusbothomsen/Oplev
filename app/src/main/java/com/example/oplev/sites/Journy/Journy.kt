package com.example.oplev.sites.Journy

import android.media.Image
import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.oplev.Model.Journey
import com.example.oplev.R
import androidx.compose.material.Text
import com.example.oplev.sites.*

import com.example.oplev.ViewModel.JourneyViewModel
import com.example.oplev.ui.theme.OplevTheme

// make an alias
typealias ComposableFun = @Composable () -> Unit


@Composable
fun JourneyScreen(journeyViewModel: JourneyViewModel, modifier: Modifier = Modifier, ) {

    Scaffold(
        topBar = {TopBar(title = "Velkommen {User}")},
        content = {
            Column(modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
            ) {
                imageAndText(text = journeyViewModel.journeyTitle(), image = null, journeyViewModel = journeyViewModel)
                gridLazytest()
            }
                /* HER SKAL LAVES MATRIX AF ROW/COLUMN TIL IDEAS*/

                  },
        bottomBar = { BottomBar()} )
}

fun createItemsForColumn(): List<ComposableFun>{


    return emptyList();
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
                .background(Color.Yellow)
                .fillMaxWidth()
        ) {
            Text(
                text = journeyViewModel.journeyTitle(),
                fontSize = 17.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
         }
        }
    }
}


@Composable
fun dynamicColumn(itemsInColumn: List<ComposableFun>){

    LazyColumn(contentPadding = PaddingValues(horizontal = 8.dp)) {

        item {
            for (x in 0 until (itemsInColumn.size)){
                Spacer(modifier = Modifier.height(5.dp))
                itemsInColumn[x]()
            }
        }

    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun gridLazytest(){
    val test: ComposableFun = {
        Box(modifier = Modifier
            .size(100.dp)
            .background(Color.Black)) {
            Button(onClick = { Log.d("HIHIH","HOHOOH")}) {
                
            }

        }
    }
    val itemsInColumn = listOf(test,test,test,test,test,test,test,test,test, test)

    LazyVerticalGrid(cells = GridCells.Fixed(3),horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(24.dp)){

        itemsInColumn.forEachIndexed{
            index, function ->  item { boxCreator(color = Color.Yellow) }
        }


    }
}
@Composable
fun boxCreator(color: Color) {
    Box(modifier = Modifier
        .size(100.dp)
        .background(color)) {
        Button(onClick = { Log.d("HIHIH", "HOHOOH") }) {

        }
    }

}
@Composable
fun folders(journeyViewModel: JourneyViewModel){

}

@Composable
fun ideas(journeyViewModel: JourneyViewModel){

}


@Composable
fun TopBar(title: String) {
    TopAppBar( modifier = Modifier.height(65.dp),
        title = { Text(title, textAlign = TextAlign.Center) },

        navigationIcon = {
            IconButton(onClick = {
                TODO()
            })
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

@Preview(showBackground = true)
@Composable
fun JourneyPreview(){
    var testJourney = Journey(tag = "test", image = null, date = null, description = "This is a test", title = "Danmark", folder = null, ideas = null)
    JourneyScreen(journeyViewModel = JourneyViewModel(testJourney))
}