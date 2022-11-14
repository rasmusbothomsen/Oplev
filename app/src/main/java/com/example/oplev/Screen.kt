package com.example.oplev

sealed class Screen(val route: String) {
    object FrontPageScreen : Screen("FrontPageScreen")
    object SignUpScreen : Screen ("SignUpScreen")
}
