package com.example.oplev.sites


import android.app.Activity
import android.content.Context
import android.location.GnssAntennaInfo
import android.media.metrics.PlaybackStateEvent.STATE_ENDED
import android.net.Uri
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.WindowInsets
import android.widget.FrameLayout
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
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
import com.google.android.exoplayer2.ExoPlayer
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import android.os.Bundle
import android.widget.Toast
import android.widget.VideoView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout.RESIZE_MODE_ZOOM
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.ui.StyledPlayerView
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util

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

    //https://www.youtube.com/watch?v=-oT9-IbPQWY&ab_channel=JurajKusnier
    val rawId = context.resources.getIdentifier("clouds", "raw", context.packageName)
    val video = "android.resource://$context.packageName/$rawId"
    val videoUri = Uri.parse(video)

    val exoPlayer = remember { context.buildExoPlayer(videoUri) }
    exoPlayer.videoScalingMode = C.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING
    // exoPlayer.repeatMode = Player.REPEAT_MODE_ALL

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
        Column(
            modifier = Modifier
                .fillMaxSize()

        ) {
            Spacer(modifier = Modifier.height(150.dp))
            Row(modifier = Modifier
                .graphicsLayer {
                    translationX = 30f
                }) {
                Image(
                    painter = painterResource(id = R.drawable.oplev_logo), contentDescription = "",
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

                OutlinedTextField(
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
                    shape = CircleShape
                )

                Spacer(modifier = Modifier.height(10.dp))

                var passwordVisible by rememberSaveable { mutableStateOf(false) }

                OutlinedTextField(
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
                        disabledIndicatorColor = Color.Transparent
                    ),
                    shape = CircleShape,
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    trailingIcon = {
                        val image = if (passwordVisible)
                            Icons.Filled.Visibility
                        else Icons.Filled.VisibilityOff
                        val description = if (passwordVisible) "Hide password" else "Show password"
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(imageVector = image, description)
                        }
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
                        runBlocking {
                            authViewModel.signIn(email, password, context, activity)
                        }
                        if (FirebaseAuth.getInstance().currentUser != null) {
                            navController.navigate(Screen.FrontPageScreen.route)
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
    }
}
