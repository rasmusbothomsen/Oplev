package com.example.oplev

sealed class Screen(val route: String) {
    object FrontPageScreen : Screen("frontpage")
    object SignUpScreen : Screen ("signup")
    object CreateJourneyScreen : Screen ("create journey")
    object JourneyScreen : Screen("journey")
    object LoginScreen : Screen("login")
    object ProfileScreen : Screen("profile")
    object CreateIdeaScreen : Screen("create idea")
    object IdeaScreen : Screen("idea")
    object Onboarding1 : Screen ("onboarding1")
    object Onboarding2 : Screen ("onboarding2")
    object Onboarding3 : Screen ("onboarding3")
    object Onboarding4 : Screen ("onboarding4")

}
