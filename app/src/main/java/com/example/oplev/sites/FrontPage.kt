package com.example.oplev.sites

import android.graphics.Paint.Align
import android.graphics.Paint.CURSOR_AT_OR_AFTER
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.oplev.Model.Journey
import com.example.oplev.R
import com.example.oplev.Screen
import com.example.oplev.ui.components.Button
import com.example.oplev.ui.components.TextFieldString

@Composable
fun FrontPageScreen(navController: NavController) {
    //Skal hentes fra firebase på et tidspunkt

    val images = listOf(
        R.drawable.img_denmark, R.drawable.img_finland,
        R.drawable.img_norway, R.drawable.img_japan
    )

    val imagesIterator = images.iterator()
    Column(
        modifier = Modifier.verticalScroll(rememberScrollState())
    ) {
        androidx.compose.material.Button(onClick = {
            navController.navigate(Screen.SignUpScreen.route)
        }) {
            Text(text = "DU ER PÅ FORSIDEN")
        }
    }

}


@Preview
@Composable
fun FrontPagePrev() {
    TopBar(title = "")
}

@Composable
fun JourneyCard(/*journey: Journey*/){
    Card(modifier = Modifier.clickable {  }, backgroundColor = Color.Yellow) {
        //Nedenunder er padding = størrelse på card.
        Column(modifier = Modifier.padding(5.dp)) {
            Box(modifier = Modifier.padding(5.dp)) {
                Image(painter = painterResource(id =  /* Her vil vi gerne have journey.GetIMG */R.drawable.img_denmark), contentDescription ="Image Denmark")
                Box(modifier = Modifier
                    .size(width = 185.dp, height = 20.dp)
                    .align(Alignment.BottomCenter)
                    .background(Color.Black.copy(alpha = 0.6f))) {
                    Text(text = "Danmark"/*journey.title*/, modifier = Modifier.align(Alignment.BottomCenter), color = Color.White)
                }

            }
            
        }
        
    }

}

@Composable
fun CategoryRow(){
    Text(text = "Seneste Ture")
    Row(modifier = Modifier.horizontalScroll(rememberScrollState())) {
        JourneyCard()
        Spacer(modifier = Modifier.width(5.dp))
        JourneyCard()
        Spacer(modifier = Modifier.width(5.dp))
        JourneyCard()
        Spacer(modifier = Modifier.width(5.dp))
        JourneyCard()
        Spacer(modifier = Modifier.width(5.dp))
        JourneyCard()
        Spacer(modifier = Modifier.width(5.dp))
        JourneyCard()
    }

}

@Composable
fun FrontPageColumn(){


    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        CategoryRow()
        Spacer(modifier = Modifier.height(5.dp))
        CategoryRow()
        Spacer(modifier = Modifier.height(5.dp))
        CategoryRow()
        Spacer(modifier = Modifier.height(5.dp))
        CategoryRow()
        Spacer(modifier = Modifier.height(5.dp))
        CategoryRow()
        Spacer(modifier = Modifier.height(5.dp))
        CategoryRow()
        Spacer(modifier = Modifier.height(5.dp))
        CategoryRow()
        Spacer(modifier = Modifier.height(5.dp))
        CategoryRow()
    }
}
@Composable
fun TopBar(title: String) {
   Scaffold(modifier = Modifier.padding(0.dp), topBar = { TopAppBar(
       title = { Text(title) },

       navigationIcon = {
           IconButton(onClick = {
               TODO()
           })
           {
               Icon(
                   painter = painterResource(id = R.drawable.oplev72dpi), contentDescription = "")




           }
       },





       backgroundColor = Color.LightGray
   ) }) {

   }
}

