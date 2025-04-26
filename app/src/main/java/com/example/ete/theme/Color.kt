package com.example.ete.theme

import androidx.compose.ui.graphics.Color
import androidx.core.graphics.toColorInt

val errorColor = hexToColor("#FF1E1E")
val errorBorder = hexToColor("#000000")
val infoColor = hexToColor("#7ADDF8")
val infoBorder = hexToColor("#000000")
val warningColor = hexToColor("#FFB620")
val warningBorder = hexToColor("#000000")
val successColor = hexToColor("#2EA92E")
val successBorder = hexToColor("#000000")

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

val white = hexToColor("#FFFFFF")
val whiteV2 = hexToColor("#E6E6E6")


val black = hexToColor("#000000")
val transparent = hexToColor("#00000000")
val black_8 = hexToColor("#00000014")
val black_10 = hexToColor("#1A000000")

val gray = hexToColor("#F2F2F2")

val grayV2 = hexToColor("#9C9C9C")
val grayV2_10 = hexToColor("#1A9C9C9C")
val grayV2_12 = hexToColor("#1F9C9C9C")
val grayV2_20 = hexToColor("#339C9C9C")

val grayV3 = hexToColor("#979797")

val red = hexToColor("#FF0000")

fun hexToColor(hex: String): Color {
    return Color(hex.toColorInt())
}

