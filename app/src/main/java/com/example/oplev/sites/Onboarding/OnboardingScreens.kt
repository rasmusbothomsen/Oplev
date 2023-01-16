package com.example.oplev.sites.Onboarding

import android.app.Activity
import android.app.Application
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.TopCenter
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.oplev.DependencyController
import com.example.oplev.MainActivity
import com.example.oplev.R
import com.example.oplev.Screen
import com.example.oplev.ViewModel.OnboadringViewModel
import com.example.oplev.ui.theme.OplevOnboard1
import com.example.oplev.ui.theme.OplevOnboard2
import com.example.oplev.ui.theme.OplevOnboard3
import com.example.oplev.ui.theme.OplevOnboard4

@Composable
fun OnboardingLayout1(navController: NavController, viewModel: OnboadringViewModel){
    Surface (modifier = Modifier.fillMaxSize()){

        Column(
            horizontalAlignment = Alignment.CenterHorizontally ,
            modifier = Modifier
                .background(color = OplevOnboard1)
                .fillMaxWidth()

        )
        {
            Box(modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)) {
                Image(
                    painter = painterResource(id = R.drawable.ob1top),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(androidx.compose.ui.Alignment.TopEnd)
                )

                Image(
                    painterResource(R.drawable.oplev72dpi),
                    contentDescription = "",
                    modifier = Modifier.size(240.dp, 240.dp)
                        .align(androidx.compose.ui.Alignment.Center)
                )

                Text(
                    text = "Velkommen til Oplev",
                    modifier = Modifier.align(BottomCenter),
                    fontSize = 40.sp,
                    fontFamily = FontFamily.Default,
                    textAlign = TextAlign.Center
                )
            }

            Box(modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)) {
                Image(
                    painter = painterResource(id = R.drawable.ob1bottom),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(androidx.compose.ui.Alignment.BottomStart)
                )

                Text(
                    text = "Oplev er appen til at planlægge ture og rejser, og dele dem med andre.",
                    modifier = Modifier
                        .align(TopCenter)
                        .padding(10.dp),
                    fontSize = 20.sp,
                    fontFamily = FontFamily.Default,
                    textAlign = TextAlign.Center

                )
                val activity = LocalContext.current as Activity

                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(50.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom
                ) {

                    ClickableText(
                        text = AnnotatedString("Spring Over"),

                        onClick = {
                            viewModel.onboadringComplete(activity)
                            navController.navigate(route = "frontpage")
                        }
                    )


                    ClickableText(
                        text = AnnotatedString("Næste",),
                        onClick = { navController.navigate(route = "onboarding2") })
                }
            }
        }
    }
}


@Composable
fun OnboardingLayout2(navController: NavController, viewModel: OnboadringViewModel){
    Surface (modifier = Modifier.fillMaxSize()){

        Column(
            horizontalAlignment = CenterHorizontally ,
            modifier = Modifier
                .background(color = OplevOnboard2)
                .fillMaxWidth()

        )
        {

            Box(modifier = Modifier
                .fillMaxWidth()
                .height(450.dp)) {

                Image(
                    painter = painterResource(id = R.drawable.ob3top),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(androidx.compose.ui.Alignment.TopEnd)
                )

                Image(
                    painterResource(R.drawable.onboardingimg1),
                    contentDescription = "",
                    modifier = Modifier.size(160.dp).align(Center)
                )

                Text(
                    text = "Lær lidt om appen inden du starter.",
                    modifier = Modifier.align(BottomCenter),
                    fontSize = 40.sp,
                    fontFamily = FontFamily.Default,
                    textAlign = TextAlign.Center
                )
            }

            Box(modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)) {

                Image(
                    painter = painterResource(id = R.drawable.ob3bottom),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(androidx.compose.ui.Alignment.BottomStart)
                )

                Text(
                    text = "De næste sider du ser, vil lære dig Oplev's kernefunktioner at kende.",
                    modifier = Modifier
                        .align(TopCenter)
                        .padding(10.dp),
                    fontSize = 20.sp,
                    fontFamily = FontFamily.Default,
                    textAlign = TextAlign.Center

                )

                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(50.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom
                ) {

                    ClickableText(
                        text = AnnotatedString("Tilbage"),


                        onClick = { navController.navigate(route = "onboarding1") },
                    )


                    ClickableText(
                        text = AnnotatedString("Næste",),
                        onClick = { navController.navigate(route = "onboarding3") })
                }
            }
        }
    }
}

@Composable
fun OnboardingLayout3(navController: NavController, viewModel: OnboadringViewModel){
    Surface (modifier = Modifier.fillMaxSize()){

        Column(
            horizontalAlignment = CenterHorizontally ,
            modifier = Modifier
                .background(color = OplevOnboard3)
                .fillMaxWidth()

        )
        {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(450.dp)
            ) {

                Image(
                    painter = painterResource(id = R.drawable.obpinkbottom),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(androidx.compose.ui.Alignment.TopEnd)
                )

                Image(
                    painterResource(R.drawable.onboardingimg2),
                    contentDescription = "",
                    modifier = Modifier.size(160.dp).align(Center)
                )

                Text(
                    text = "Opret eventyr",
                    modifier = Modifier.align(BottomCenter),
                    fontSize = 40.sp,
                    fontFamily = FontFamily.Default,
                    textAlign = TextAlign.Center

                )
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp)
            ) {

                Image(
                    painter = painterResource(id = R.drawable.obpinktop),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(androidx.compose.ui.Alignment.BottomStart)
                )

                Text(
                    text = "Plus-ikonet i bunden af din side, er hvor du starter din egen rejse. Når du klikker på den, kan du oprette en rejse, eller en rejsekategori.",
                    modifier = Modifier
                        .align(TopCenter)
                        .padding(10.dp),
                    fontSize = 20.sp,
                    fontFamily = FontFamily.Default,
                    textAlign = TextAlign.Center

                )


                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(50.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom
                ) {

                    ClickableText(
                        text = AnnotatedString("Tilbage"),


                        onClick = { navController.navigate(route = "onboarding2") },
                    )


                    ClickableText(
                        text = AnnotatedString("Næste",),
                        onClick = { navController.navigate(route = "onboarding4") })
                }
            }
        }
    }
}

@Composable
fun OnboardingLayout4(navController: NavController, viewModel: OnboadringViewModel) {
    Surface(modifier = Modifier.fillMaxSize()) {

        Column(
            horizontalAlignment = CenterHorizontally,
            modifier = Modifier
                .background(color = OplevOnboard4)
                .fillMaxWidth()

        )
        {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp)
            ) {

                Image(
                    painter = painterResource(id = R.drawable.ob3top),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(androidx.compose.ui.Alignment.TopEnd)
                )

                Image(
                    painterResource(R.drawable.onboardingimg3),
                    contentDescription = "",
                    modifier = Modifier.size(150.dp, 120.dp)
                        .align(Center)
                )


                Text(
                    text = "Del dine rejser.",
                    modifier = Modifier.align(BottomCenter),
                    fontSize = 40.sp,
                    fontFamily = FontFamily.Default,
                    textAlign = TextAlign.Center

                )

            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp)
            ) {

                Image(
                    painter = painterResource(id = R.drawable.ob3bottom),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(androidx.compose.ui.Alignment.BottomStart)
                )

                Text(
                    text = "Del dine rejser med andre brugere. Invitér dem du vil have med på dit eventyr. ",
                    modifier = Modifier
                        .align(TopCenter)
                        .padding(10.dp),
                    fontSize = 20.sp,
                    fontFamily = FontFamily.Default,
                    textAlign = TextAlign.Center

                )

                val activity = LocalContext.current as Activity
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(50.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom
                ) {

                    ClickableText(
                        text = AnnotatedString("Tilbage"),


                        onClick = { navController.navigate(route = "onboarding3") },
                    )
                    ClickableText(
                        text = AnnotatedString("Slut",), onClick = {
                            viewModel.onboadringComplete(activity)
                            navController.navigate(route = "frontpage")
                        })
                }
            }
        }
    }
}

@Preview
@Composable
fun ob1() {
    Surface (modifier = Modifier.fillMaxSize()){

        Column(
            horizontalAlignment = CenterHorizontally ,
            modifier = Modifier
                .background(color = OplevOnboard2)
                .fillMaxWidth()

        )
        {
            Box(modifier = Modifier
                .fillMaxWidth()
                .height(500.dp)) {

                Image(
                    painter = painterResource(id = R.drawable.ob3top),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(androidx.compose.ui.Alignment.TopEnd)
                )

                Image(
                    painterResource(R.drawable.onboardingimg1),
                    contentDescription = "",
                    modifier = Modifier.size(160.dp)
                        .align(androidx.compose.ui.Alignment.Center)
                )
                /* Headline */
                Text(
                    text = "Lær lidt om appen inden du starter.",
                    modifier = Modifier.align(BottomCenter),
                    fontSize = 40.sp,
                    fontFamily = FontFamily.Default,
                    textAlign = TextAlign.Center

                )
            }

            Box(modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)) {
                Image(
                    painter = painterResource(id = R.drawable.ob3bottom),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(androidx.compose.ui.Alignment.BottomStart)
                )

                Text(
                    text = "De næste sider du ser, vil lære dig Oplev's kernefunktioner at kende.",
                    modifier = Modifier
                        .align(TopCenter)
                        .padding(10.dp),
                    fontSize = 20.sp,
                    fontFamily = FontFamily.Default,
                    textAlign = TextAlign.Center

                )

                Row(modifier= Modifier
                    .fillMaxSize()
                    .padding(50.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.Bottom) {

                    ClickableText(
                        text = AnnotatedString("Tilbage"),


                        onClick = {},
                    )


                    ClickableText(
                        text = AnnotatedString("Næste", ), onClick = { })
                }
            }


        }
    }
}
