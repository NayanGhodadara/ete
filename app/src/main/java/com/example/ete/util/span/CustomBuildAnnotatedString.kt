package com.example.ete.util.span

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import com.example.ete.theme.black
import com.example.ete.theme.red


class CustomBuildAnnotatedString {

    private var completeString = ""

    companion object {
        var instance: CustomBuildAnnotatedString = CustomBuildAnnotatedString()
    }

    fun setCompleteString(string1: String): CustomBuildAnnotatedString {
        completeString = string1
        return this
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

    @Composable
    fun setFontFamily(string1: String, type: TextStyle): AnnotatedString {
        val fullText = completeString
        val annotatedString = buildAnnotatedString {
            val startIndex = fullText.indexOf(string1)
            if (startIndex >= 0) {
                // Before the bold part
                append(fullText.substring(0, startIndex))

                // Bold part
                withStyle(
                    style = SpanStyle(
                        color = black,
                        fontFamily = type.fontFamily
                    )
                ) {
                    append(string1)
                }

                // After the bold part
                append(fullText.substring(startIndex + string1.length))
            } else {
                // Just show full text if substring not found
                append(fullText)
            }
        }
        return annotatedString
    }
}