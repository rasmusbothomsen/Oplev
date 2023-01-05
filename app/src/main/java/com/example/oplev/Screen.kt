package com.example.oplev

sealed class Screen(val route: String) {
    object FrontPageScreen : Screen("frontpage")
    object SignUpScreen : Screen ("signup")
    object CreateJourneyScreen : Screen ("create journey")
    object JourneyScreen : Screen("journey")
    object LoginScreen : Screen("login")
    object ProfileScreen : Screen("profile")
}
