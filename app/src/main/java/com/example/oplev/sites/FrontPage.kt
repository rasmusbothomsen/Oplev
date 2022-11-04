package com.example.oplev.sites

import androidx.compose.foundation.Image
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.oplev.R

class FrontPage {
}
@Composable
fun FrontPageComp() {
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
        Row(modifier = Modifier.horizontalScroll(rememberScrollState())) {
            while (imagesIterator.hasNext())
                Image(painterResource(id = imagesIterator.next()), "cd")

        }
        Row(modifier = Modifier.horizontalScroll(rememberScrollState())) {
            while (imagesIterator.hasNext())
                Image(painterResource(id = imagesIterator.next()), "cd")

        }
        Row(modifier = Modifier.horizontalScroll(rememberScrollState())) {
            while (imagesIterator.hasNext())
                Image(painterResource(id = imagesIterator.next()), "cd")

        }
    }
}