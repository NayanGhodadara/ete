package com.example.ete.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.ete.R

val sans = FontFamily(
    Font(R.font.nunito_sans_extra_light, FontWeight.ExtraLight),// 100
    Font(R.font.nunito_sans_light, FontWeight.Light),           // 300
    Font(R.font.nunito_sans_regular, FontWeight.Normal),        // 400
    Font(R.font.nunito_sans_medium, FontWeight.Medium),         // 500
    Font(R.font.nunito_sans_semi_bold, FontWeight.SemiBold),    // 600
    Font(R.font.nunito_sans_bold, FontWeight.Bold),             // 700
    Font(R.font.nunito_sans_extra_bold, FontWeight.ExtraBold),  // 800
    Font(R.font.nunito_sans_black, FontWeight.Black)            // 900
)

val Typography = Typography(

    //100-Light
    labelSmall = TextStyle(
        fontFamily = sans,
        fontWeight = FontWeight.Thin,
        fontSize = 14.sp
    ),

    //300-Light
    labelMedium = TextStyle(
        fontFamily = sans,
        fontWeight = FontWeight.Light,
        fontSize = 14.sp
    ),

    //400-Regular
    bodyLarge = TextStyle(
        fontFamily = sans,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp
    ),

    //500-Medium
    titleMedium = TextStyle(
        fontFamily = sans,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp
    ),

    //600-SemiBold
    titleLarge = TextStyle(
        fontFamily = sans,
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp
    ),

    //700-Bold
    headlineSmall = TextStyle(
        fontFamily = sans,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp
    ),

    //800-ExtraBold
    headlineMedium = TextStyle(
        fontFamily = sans,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 14.sp
    ),

    //900-Black
    headlineLarge = TextStyle(
        fontFamily = sans,
        fontWeight = FontWeight.Black,
        fontSize = 24.sp
    )
)