package com.example.oplev.sites

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
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
        modifier = Modifier
            .verticalScroll(rememberScrollState())
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
}