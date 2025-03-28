package com.example.ete.theme

import androidx.compose.ui.graphics.Color
import androidx.core.graphics.toColorInt

val errorColor = hexToColor("#D50000")
val infoColor = hexToColor("#08AAD2")
val warningColor = hexToColor("#FFA900")
val successColor = hexToColor("#388E3C")

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

val white = hexToColor("#FFFFFF")
val whiteV2 = hexToColor("#E6E6E6")


val black = hexToColor("#000000")
val black_10 = hexToColor("#1A000000")

val gray = hexToColor("#F2F2F2")

val grayV2 = hexToColor("#9C9C9C")
val grayV2_10 = hexToColor("#1A9C9C9C")
val grayV2_12 = hexToColor("#1F9C9C9C")

val red = hexToColor("#FF0000")

fun hexToColor(hex: String): Color {
    return Color(hex.toColorInt())
}

