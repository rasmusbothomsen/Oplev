package com.example.oplev.sites.Onboarding

import androidx.annotation.DrawableRes
import com.example.oplev.R

// Denne klasse er til dependency injection. Forsøger at lave denne sealed klasse til alle onboarding pages.

sealed class OnboardingElements(

    @DrawableRes
    val image: Int,
    val title: String,
    val description: String
){
    object FirstOnBoardScreen : OnboardingElements(
        image = R.drawable.oplev300dpi,
        title = "Screen one",
        description = "Screen one description"
    )

    object SecondOnBoardScreen : OnboardingElements(
        image = R.drawable.oplev300dpi,
        title = "Screen two",
        description = "Screen two description"
    )

    object ThirdOnBoardSreen : OnboardingElements(
        image = R.drawable.oplev300dpi,
        title = "Screen three",
        description = "Screen three description"
    )
}