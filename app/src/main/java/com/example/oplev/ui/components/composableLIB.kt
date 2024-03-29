package com.example.oplev.ui.components

import android.app.Application
import android.app.DatePickerDialog
import android.content.Context
import android.service.autofill.UserData
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.core.graphics.toColorInt
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.oplev.DependencyController
import com.example.oplev.Model.Journey
import com.example.oplev.Model.UserInfo
import com.example.oplev.R
import com.example.oplev.Screen
import com.example.oplev.ViewModel.OnboadringViewModel
import com.example.oplev.data.dataService.UserDataService
import com.example.oplev.sites.*
import com.example.oplev.sites.Idea.CreateIdea
import com.example.oplev.sites.Idea.IdeaScreen
import com.example.oplev.sites.Journy.JourneyScreen
import com.example.oplev.sites.Journy.createJourneyComp
import com.example.oplev.sites.Onboarding.OnboardingLayout1
import com.example.oplev.sites.Onboarding.OnboardingLayout2
import com.example.oplev.sites.Onboarding.OnboardingLayout3
import com.example.oplev.sites.Onboarding.OnboardingLayout4
import com.google.firebase.firestore.auth.User
import java.time.LocalDate
import java.time.LocalTime
import java.util.*

data class sharedUiState(
    var choosenJourney:Journey? = null
)

@Composable
fun NavController(application: Application, startRoute : String) {
    val navController = rememberNavController()
    var dependencyController = DependencyController()
    var context = LocalContext.current.applicationContext as android.content.Context
    var uiState = sharedUiState()



    NavHost(navController = navController, startDestination = startRoute) {
        composable(route = Screen.FrontPageScreen.route) {
            TotalView(frontpageViewModel = dependencyController.initFrontPageViewModel(context,application), navController)
            BackHandler(true) {
            }
        }
        composable(route = Screen.CreateJourneyScreen.route) {
            createJourneyComp(createJourneyViewModel = dependencyController.initCreateJourneyViewModel(context,application), navController )
        }
        composable(route = Screen.JourneyScreen.route+"/{id}"){navBackStack ->
            val journeyID = navBackStack.arguments?.getString("id")
            JourneyScreen(journeyViewModel = dependencyController.intiJourneyViewModel(context, application,journeyID!!), navController)
        }
        composable(route = Screen.SignUpScreen.route){
            CreateUserView(authViewModel = dependencyController.initAuthViewModel(context, application) , navController = navController)
        }
        composable(route = Screen.LoginScreen.route){
            LoginView(authViewModel = dependencyController.initAuthViewModel(context, application), navController = navController)

        }
        composable(route = Screen.ProfileScreen.route){
            ProfileView(authViewModel = dependencyController.initAuthViewModel(context, application), navController = navController)
        }
        composable(route = Screen.IdeaScreen.route+"/{id}"){navBackStack ->
            val ideaId = navBackStack.arguments?.getString("id")
            IdeaScreen(ideaViewModel = dependencyController.initIdeaViewModel(context, application,ideaId!!), navController)
        }
        composable(route = Screen.CreateIdeaScreen.route+"/{id}"){navBackStack ->
            val folderId = navBackStack.arguments?.getString("id")
            CreateIdea(createIdeaViewModel = dependencyController.initCreateIdeaViewModel(context, application,folderId!!), navController)
        }


        composable(route = Screen.Onboarding1.route){
            OnboardingLayout1(navController = navController, viewModel = dependencyController.initOnboardingViewModel(context, application))
        }
        composable(route = Screen.Onboarding2.route){
            OnboardingLayout2(navController = navController, viewModel = dependencyController.initOnboardingViewModel(context, application))
        }
        composable(route = Screen.Onboarding3.route){
            OnboardingLayout3(navController = navController, viewModel = dependencyController.initOnboardingViewModel(context, application))
        }
        composable(route = Screen.Onboarding4.route){
            OnboardingLayout4(navController = navController, viewModel = dependencyController.initOnboardingViewModel(context, application))
        }
    }


}



@Composable
fun Button(text: String, width: Int, height: Int, hexCode: String, onClick: Unit ){
    Button(
        onClick = {
            onClick
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


@Composable
fun FolderGrid() {
}


//"tekst knap"

@Composable
fun Button(text: String, width: Int, height: Int, onClick: () -> Unit){
    Button(onClick = onClick,
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
fun VerticalScroll( ){
    Column(modifier = Modifier
        .verticalScroll(rememberScrollState())
        .fillMaxWidth()
        .fillMaxHeight()){
        Image(painterResource(id = R.drawable.img_norway), "cd" )
        Image(painterResource(id = R.drawable.img_norway), "cd" )
    }
}

@Composable
fun HorizontalScroll( ){
    Row(modifier = Modifier.horizontalScroll(rememberScrollState())) {
        Image(painterResource(id = R.drawable.img_norway), "cd" )
        Image(painterResource(id = R.drawable.img_norway), "cd" )
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

@Composable
fun DateandTimePicker(){
    var date by remember { mutableStateOf(LocalDate.now()) }
    var time by remember { mutableStateOf(LocalTime.now()) }
    Column {
        Text(text = "Date: $date")
        Text(text = "Time: $time")
        Button(onClick = { date = LocalDate.now() }) {
            Text(text = "Pick Date")
        }
        Button(onClick = { time = LocalTime.now() }) {
            Text(text = "Pick Time")
        }
    }
}

@Preview
@Composable
fun DateandTimePickerPreview() {
    DateandTimePicker()
}