package com.example.oplev.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.oplev.R

val fonts = FontFamily(
    Font(R.font.oswald_semibold),
)
// Set of Material typography styles to start with
val Typography = Typography(
    h1 = TextStyle(
        fontFamily = fonts,
        fontSize = 64.sp
    ),
    caption = TextStyle()
)

val fonts1 = FontFamily(
    Font(R.font.roboto_regular),
)
// Set of Material typography styles to start with
val Typography1 = Typography(
    h1 = TextStyle(
        fontFamily = fonts1,
        fontSize = 16.sp
    ),
    caption = TextStyle()
)


val fontsbold = FontFamily(
    Font(R.font.roboto_bold),
)
val fontsmedium = FontFamily(
    Font(R.font.roboto_medium)
)
