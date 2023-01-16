package com.example.oplev.sites


import android.app.Activity
import android.content.Context
import android.net.Uri
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.FrameLayout
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.graphics.toColorInt
import androidx.navigation.NavController
import com.example.oplev.MainActivity.Instance.context
import com.example.oplev.R
import com.example.oplev.Screen
import com.example.oplev.ViewModel.AuthViewModel
import com.example.oplev.ui.theme.fonts
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import android.widget.VideoView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.example.oplev.data.dataService.UserDataService
import com.example.oplev.ui.theme.Farvekombi032
import com.example.oplev.ui.theme.Farvekombi033
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout.RESIZE_MODE_ZOOM
import com.google.android.exoplayer2.ui.StyledPlayerView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.runBlocking

@Composable
fun LoginView(authViewModel: AuthViewModel, navController: NavController) {
    val scaffoldstate = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldstate,
        content = { LoginContent(authViewModel, navController) },
        drawerContent = {
        },
    )
}

private fun Context.buildExoPlayer(uri: Uri) =
    ExoPlayer.Builder(this).build().apply {
        setMediaItem(MediaItem.fromUri(uri))
        repeatMode = Player.REPEAT_MODE_ALL
        playWhenReady = true
        videoScalingMode = C.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING
        prepare()
    }

private fun Context.buildPlayerView(exoPlayer: ExoPlayer) =
    StyledPlayerView(this).apply {
        player = exoPlayer
        layoutParams = FrameLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT)
        useController = false
        resizeMode = RESIZE_MODE_ZOOM
    }

@Composable
fun LoginContent(authViewModel: AuthViewModel, navController: NavController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val backgroundColor = Color(("#ECC5C9").toColorInt())
    val logotextcol = Color(("#004070").toColorInt())
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val state = authViewModel.state.value
    val loading = authViewModel.isLoading.value
    val syncDone = authViewModel.syncdone.value


    //https://www.youtube.com/watch?v=-oT9-IbPQWY&ab_channel=JurajKusnier
    val rawId = context.resources.getIdentifier("clouds", "raw", context.packageName)
    val video = "android.resource://$context.packageName/$rawId"
    val videoUri = Uri.parse(video)

    val exoPlayer = remember { context.buildExoPlayer(videoUri) }
    exoPlayer.videoScalingMode = C.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING
    // exoPlayer.repeatMode = Player.REPEAT_MODE_ALL


    if (syncDone) {
        navController.navigate(Screen.FrontPageScreen.route)
    }


    DisposableEffect(
        AndroidView(
            factory = { it.buildPlayerView(exoPlayer) },
            modifier = Modifier.fillMaxSize()
        )
    ) {
        onDispose {
            exoPlayer.release()
        }
    }

    ProvideWindowInsets {
    }
    Column(
        modifier = Modifier
            .fillMaxSize().verticalScroll(rememberScrollState()).blur(radius = state.isLoadingBlur)

    ) {
        Spacer(modifier = Modifier.height(150.dp))
        Row(modifier = Modifier
            .graphicsLayer {
                translationX = 30f
            }) {
            Image(
                painter = painterResource(id = R.drawable.oplev_logo),
                contentDescription = "",
                modifier = Modifier
                    .height(102.dp)
                    .width(113.dp)
                    .padding(top = 0.dp)
            )
            Text(
                text = "OPLEV",
                fontSize = 64.sp,
                fontFamily = fonts,
                letterSpacing = 10.sp,
                fontWeight = FontWeight.SemiBold,
                color = logotextcol
            )
        }

        Spacer(modifier = Modifier.height(50.dp))
        //var dialogstate by rememberSaveable { mutableStateOf(false) }

        Column() {
            TextField(
                value = email,
                label = { Text(text = "Email", textAlign = TextAlign.Center) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(70.dp, 0.dp, 70.dp, 0.dp),
                onValueChange = {
                    email = it
                },
                singleLine = true,
                colors = TextFieldDefaults.textFieldColors(
                    textColor = Color.Black,
                    backgroundColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
                shape = CircleShape,
                 leadingIcon = {
                     Icon (
                         imageVector = Icons.Default.Email, contentDescription = "", tint = Farvekombi033
                             )
                 }
            )


            Spacer(modifier = Modifier.height(10.dp))

            var passwordVisible by rememberSaveable { mutableStateOf(false) }

            TextField(
                value = password,
                label = { Text(text = "Kodeord", textAlign = TextAlign.Center) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(70.dp, 0.dp, 70.dp, 0.dp),
                onValueChange = {
                    password = it
                },
                singleLine = true,
                colors = TextFieldDefaults.textFieldColors(
                    textColor = Color.Black,
                    backgroundColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,

                ),
                shape = CircleShape,
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                trailingIcon = {
                    val image = if (passwordVisible)
                        Icons.Filled.Visibility
                    else Icons.Filled.VisibilityOff
                    val description =
                        if (passwordVisible) "Hide password" else "Show password"
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(imageVector = image, description)
                    }
                },
                        leadingIcon = {
                    Icon (
                        imageVector = Icons.Default.Lock, contentDescription = "", tint = Farvekombi033
                    )
                }
            )



            TextButton(
                onClick = {
                    if (!email.isEmpty()) {
                        runBlocking {
                            authViewModel.sendPasswordReset(email)
                            authViewModel.forgotPasswordStateChange()
                        }
                    }
                },
                modifier = Modifier
                    .padding(195.dp, 0.dp, 70.dp, 0.dp)
            ) {
                Text(
                    text = "Glemt kodeord",
                    color = Color.Black
                )
            }

        }

        val context = LocalContext.current
        val activity = LocalContext.current as Activity

        Spacer(modifier = Modifier.height(50.dp))

        Button(
            onClick = {
                if (!email.isEmpty() && !password.isEmpty()) {
                    val success = authViewModel.signIn(email, password, context, activity)
                    when (success) {
                        is UserDataService.SignInResult.Success -> {
                            if (FirebaseAuth.getInstance().currentUser != null) {
                                authViewModel.syncDatabases()
                            }
                            authViewModel.loadingBlurChange()
                        }
                        is UserDataService.SignInResult.WrongCredentials -> {
                            Log.e("Failed login", "Wrong credentials")
                        }
                        is UserDataService.SignInResult.Failed -> {
                            Log.e("Failed login", "FirebaseFailed")
                        }
                    }
                }
            }, shape = CircleShape,
            colors = ButtonDefaults.buttonColors(backgroundColor = logotextcol),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .padding(90.dp, 0.dp, 90.dp, 0.dp)
        ) {
            Text(text = "LOG IND", fontSize = 16.sp, color = Color.White)
        }

        TextButton(
            onClick = {
                navController.navigate(Screen.SignUpScreen.route)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(90.dp, 15.dp, 90.dp, 0.dp)
        ) {
            Text(text = "OPRET BRUGER", fontSize = 16.sp, color = Color.Black)
        }

//TODO ER TAGET FRA ASOS. SKAL ÆNDRES
        if (authViewModel.state.value.forgotpassworddialog) {
            AlertDialog(
                onDismissRequest = { /*TODO*/ },
                title = { Text(text = "LINK TIL NULSTILLING AF ADGANGSKODE ER SENDT") },
                text = {
                    Text(
                        text =
                        "Vi har sendt dig en e-mail til nulstilling af din adgangskode\n" +
                                "\n" +
                                "For at oprette din nye adgangskode skal du klikke på linket i e-mailen og angive en ny – pærenemt\n" +
                                "\n" +
                                "Har du ikke modtaget e-mailen? Tjek din spammappe."
                    )
                },
                confirmButton = {
                    Button(onClick = {
                        authViewModel.forgotPasswordStateChange()
                    }) {
                        Text(text = "OK")
                    }
                }
            )
        }
    }
    if (loading) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            LoadingAnimation()
        }
    }
}


@Composable
fun LoadingAnimation(
    indicatorSize: Dp = 100.dp,
    circleColors: List<Color> = listOf(
        Color(0xFF5851D8),
        Color(0xFF833AB4),
        Color(0xFFC13584),
        Color(0xFFE1306C),
        Color(0xFFFD1D1D),
        Color(0xFFF56040),
        Color(0xFFF77737),
        Color(0xFFFCAF45),
        Color(0xFFFFDC80),
        Color(0xFF5851D8)
    ),
    animationDuration: Int = 360
) {

    val infiniteTransition = rememberInfiniteTransition()

    val rotateAnimation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = animationDuration,
                easing = LinearEasing
            )
        )
    )

    CircularProgressIndicator(
        modifier = Modifier
            .size(size = indicatorSize)
            .rotate(degrees = rotateAnimation)
            .border(
                width = 4.dp,
                brush = Brush.sweepGradient(circleColors),
                shape = CircleShape
            ),
        progress = 1f,
        strokeWidth = 1.dp,
        color = MaterialTheme.colors.background // Set background color
    )
}

@Preview
@Composable
fun loadingTest(){
    LoadingAnimation()
}
