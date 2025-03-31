package com.example.ete.util.span

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import com.example.ete.theme.black
import com.example.ete.theme.red


class CustomBuildAnnotatedString {

    companion object {
        var instance: CustomBuildAnnotatedString = CustomBuildAnnotatedString()
    }

    @Composable
    fun getReqString(string1: String): AnnotatedString {
        return buildAnnotatedString {
            withStyle(style = SpanStyle(color = black)) {
                append(string1)
            }
            withStyle(style = SpanStyle(color = red)) {
                append("*")
            }
        }
    }
}