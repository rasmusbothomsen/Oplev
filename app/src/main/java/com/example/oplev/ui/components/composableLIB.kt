package com.example.oplev.ui.components

import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import com.example.oplev.ui.theme.OplevTheme

class composableLIB {

}
@Composable
fun Button(text: String,  width: Int, height: Int, hexCode: String){
    Button(
        onClick = {
            TODO()
        },
        colors = ButtonDefaults.buttonColors(backgroundColor = Color(hexCode.toColorInt()), contentColor = Color.White),
        modifier = Modifier
            .height(height.dp)
            .width(width.dp),
        shape = RoundedCornerShape(15),
        elevation = ButtonDefaults.elevation(4.dp)
    )

    {
        Text(text = text)
    }
}


//"tekst knap"
@Composable
fun Button(text: String, width: Int, height: Int){
    Button(onClick = {
        TODO()
    },
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.Transparent,
            contentColor = Color.Black), modifier = Modifier
            .height(height.dp)
            .width(width.dp),
        elevation = ButtonDefaults.elevation(0.dp)
    ) {
        Text(text = text,
            modifier = Modifier.alpha(ContentAlpha.medium)
        )
    }
}


@Composable
fun Fab(color: Color, text: String){
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                TODO()
            }, backgroundColor = color,
                modifier = Modifier.border(0.2.dp, Color.Black, CircleShape)) {
                Text(text, fontSize = 30.sp)
            }
        }
    ) {

    }
}


@Composable
fun TextFieldString (text: String, width: Int, height: Int) {
    var text by remember { mutableStateOf(TextFieldValue(text)) }
    TextField(
        value = text,
        onValueChange = { newText ->
            text = newText
        }, modifier = Modifier
            .height(height.dp)
            .width(width.dp), textStyle = TextStyle.Default.copy(fontSize = 18.sp)
    )
}

@Composable
fun TopBar(title: String) {
    TopAppBar(
        title = { Text(title) },

        navigationIcon = {
            IconButton(onClick = {
                TODO()
            })
            {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = "Localized description",
                    tint = Color.White
                )
            }
        },
        actions = {
            IconButton(onClick = {

                TODO() }
            )

            {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Localized description",
                    tint = Color.White
                )
            }
        },
        backgroundColor = Color.LightGray
    )
}
/*
@Composable
fun VerticalScroll( ){
        Column(modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxWidth()
            .fillMaxHeight()){
            Image(painterResource(id = R.drawable.img_image6), "cd" )
            Image(painterResource(id = R.drawable.img_image6), "cd" )
    }
}

@Composable
fun HorizontalScroll( ){
    Row(modifier = Modifier.horizontalScroll(rememberScrollState())) {
        Image(painterResource(id = R.drawable.img_image6), "cd" )
        Image(painterResource(id = R.drawable.img_image6), "cd" )
    }
}

*/

@Composable
fun FrontPage() {
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

//Den her virker ikke helt som jeg vil ha den til endnu, men tror der er en bedre guide på m3
@Composable
fun ExposedDropdownMenu(){
    var expanded by remember { mutableStateOf(false)
    }
    val list = listOf("Asien", "Europa", "Afrika")
    var selectedItem by remember { mutableStateOf("") }
    var textFilledSize by remember { mutableStateOf(Size.Zero) }
    val icon = if (expanded)
    { Icons.Filled.KeyboardArrowUp }
    else {
        Icons.Filled.KeyboardArrowDown
    }

    OutlinedTextField(value = selectedItem,
        onValueChange = { selectedItem = it },
        modifier = Modifier
            .fillMaxWidth()
            .onGloballyPositioned { coordinates -> textFilledSize = coordinates.size.toSize() },
        label = { Text(text = "select item") },
        trailingIcon = {
            Icon(icon, "", Modifier.clickable { expanded = !expanded })
        }
    )
    DropdownMenu(expanded = expanded,
        onDismissRequest = { expanded = false },
        modifier = Modifier.width(with(LocalDensity.current){textFilledSize.width.toDp()})
    ) {
        list.forEach { label ->
            DropdownMenuItem(onClick = {
                selectedItem = label
                expanded = false
            }) {
                Text(text = label)

            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyApplicationTheme {
        FrontPage()
    }
}