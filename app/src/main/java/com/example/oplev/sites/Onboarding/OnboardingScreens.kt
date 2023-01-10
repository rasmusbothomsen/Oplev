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
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
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
            Spacer(Modifier.height(120.dp))
            Image(
                painterResource(R.drawable.oplev300dpi),
                contentDescription = "",
                modifier = Modifier.size(240.dp, 240.dp)
            )
            Spacer(Modifier.height(30.dp))


            Text(
                text = "Velkommen til Oplev",
                modifier = Modifier.align(Alignment.CenterHorizontally),
                fontSize = 40.sp,
                fontFamily = FontFamily.Default,
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(30.dp))
            Text(
                text = "Oplev er appen til at planlægge ture og rejser, og dele dem med andre.",
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(10.dp),
                fontSize = 28.sp,
                fontFamily = FontFamily.Default,
                textAlign = TextAlign.Center

            )


            Row(modifier= Modifier
                .fillMaxSize()
                .padding(50.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.Bottom) {

                ClickableText(
                    text = AnnotatedString("Spring Over"),


                    onClick = {viewModel.onboadringComplete()
                        navController.navigate(route = "frontpage") }
                )


                ClickableText(
                    text = AnnotatedString("Næste", ), onClick = { navController.navigate(route = "onboarding2")})
            }
        }
    }
}


@Composable
fun OnboardingLayout2(navController: NavController, viewModel: OnboadringViewModel){
    Surface (modifier = Modifier.fillMaxSize()){

        Column(
            horizontalAlignment = Alignment.CenterHorizontally ,
            modifier = Modifier
                .background(color = OplevOnboard2)
                .fillMaxWidth()

        )
        {
            Spacer(Modifier.height(120.dp))
            Image(
                painterResource(R.drawable.onboardingimg1),
                contentDescription = "",
                modifier = Modifier.size(240.dp, 240.dp)
            )
            Spacer(Modifier.height(30.dp))
            /* Headline */

            Text(
                text = "Lær lidt om appen inden du starter.",
                modifier = Modifier.align(Alignment.CenterHorizontally),
                fontSize = 40.sp,
                fontFamily = FontFamily.Default,
                textAlign = TextAlign.Center

            )
            Spacer(Modifier.height(30.dp))
            Text(
                text = "De næste sider du ser vil lære dig Oplev's kernefunktioner at kende.",
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(10.dp),
                fontSize = 28.sp,
                fontFamily = FontFamily.Default,
                textAlign = TextAlign.Center

            )


            Row(modifier= Modifier
                .fillMaxSize()
                .padding(50.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.Bottom) {

                ClickableText(
                    text = AnnotatedString("Tilbage"),


                    onClick = {navController.navigate(route= "onboarding1")},
                )


                ClickableText(
                    text = AnnotatedString("Næste", ), onClick = { navController.navigate(route = "onboarding3") })
            }
        }
    }
}

@Composable
fun OnboardingLayout3(navController: NavController, viewModel: OnboadringViewModel){
    Surface (modifier = Modifier.fillMaxSize()){

        Column(
            horizontalAlignment = Alignment.CenterHorizontally ,
            modifier = Modifier
                .background(color = OplevOnboard3)
                .fillMaxWidth()

        )
        {
            Spacer(Modifier.height(120.dp))
            Image(
                painterResource(R.drawable.onboardingimg1),
                contentDescription = "",
                modifier = Modifier.size(240.dp, 240.dp)
            )
            Spacer(Modifier.height(30.dp))
            /* Headline */

            Text(
                text = "Opret eventyr",
                modifier = Modifier.align(Alignment.CenterHorizontally),
                fontSize = 40.sp,
                fontFamily = FontFamily.Default,
                textAlign = TextAlign.Center

            )
            Spacer(Modifier.height(30.dp))
            Text(
                text = "Plus-ikonet i bunden af din side, er hvor du starter din egen rejse. Når du klikker på den, kan du oprette en rejse, eller en rejsekategori.",
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(10.dp),
                fontSize = 28.sp,
                fontFamily = FontFamily.Default,
                textAlign = TextAlign.Center

            )


            Row(modifier= Modifier
                .fillMaxSize()
                .padding(50.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.Bottom) {

                ClickableText(
                    text = AnnotatedString("Tilbage"),


                    onClick = {navController.navigate(route = "onboarding2")},
                )


                ClickableText(
                    text = AnnotatedString("Næste", ), onClick = { navController.navigate(route = "onboarding4") })
            }
        }
    }
}

@Composable
fun OnboardingLayout4(navController: NavController, viewModel: OnboadringViewModel){
    Surface (modifier = Modifier.fillMaxSize()){

        Column(
            horizontalAlignment = Alignment.CenterHorizontally ,
            modifier = Modifier
                .background(color = OplevOnboard4)
                .fillMaxWidth()

        )
        {
            Spacer(Modifier.height(120.dp))
            Image(
                painterResource(R.drawable.onboardingimg1),
                contentDescription = "",
                modifier = Modifier.size(240.dp, 240.dp)
            )
            Spacer(Modifier.height(30.dp))
            /* Headline */

            Text(
                text = "Del dine rejser.",
                modifier = Modifier.align(Alignment.CenterHorizontally),
                fontSize = 40.sp,
                fontFamily = FontFamily.Default,
                textAlign = TextAlign.Center

            )
            Spacer(Modifier.height(30.dp))
            Text(
                text = "Del dine rejser med andre brugere. Invitér dem du vil have med på dit eventyr. ",
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(10.dp),
                fontSize = 28.sp,
                fontFamily = FontFamily.Default,
                textAlign = TextAlign.Center

            )


            Row(modifier= Modifier
                .fillMaxSize()
                .padding(50.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.Bottom) {

                ClickableText(
                    text = AnnotatedString("Tilbage"),


                    onClick = {navController.navigate(route = "onboarding3")},
                )


                ClickableText(
                    text = AnnotatedString("Slut", ), onClick = { viewModel.onboadringComplete()
                        navController.navigate(route = "frontpage")})
            }
        }
    }
}

