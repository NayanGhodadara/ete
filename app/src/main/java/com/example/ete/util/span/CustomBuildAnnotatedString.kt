package com.example.ete.util.span

import android.graphics.Typeface
import android.text.Spannable
import android.text.style.CharacterStyle
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import com.example.ete.theme.black
import com.example.ete.theme.red


class CustomBuildAnnotatedString {

    private var completeString = ""
    private var editedString = arrayListOf<String>()
    private var annotatedString = buildAnnotatedString { }

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
    fun setString(vararg str: String): CustomBuildAnnotatedString {
        editedString.addAll(str)
        return this
    }

    @Composable
    fun setFontStyle(fontFamily: FontFamily, color: Color = black): CustomBuildAnnotatedString {
        val styleRanges = mutableListOf<Pair<Int, Int>>()

        for (word in editedString) {
            if (word.isEmpty()) continue
            var startIndex = completeString.indexOf(word)
            while (startIndex >= 0) {
                styleRanges.add(startIndex to startIndex + word.length)
                startIndex = completeString.indexOf(word, startIndex + 1)
            }
        }

        val annotatedStr = buildAnnotatedString {
            var index = 0

            while (index < completeString.length) {
                val styleRange = styleRanges.find { it.first == index }

                if (styleRange != null) {
                    withStyle(
                        style = SpanStyle(
                            fontFamily = fontFamily,
                            color = color
                        ),
                    ) {
                        append(completeString.substring(styleRange.first, styleRange.second))
                    }
                    index = styleRange.second
                } else {
                    append(completeString[index])
                    index++
                }
            }
        }

        annotatedString = annotatedStr
        return this
    }

    @Composable
    fun setColor(color: Color): CustomBuildAnnotatedString {
        val styleRanges = mutableListOf<Pair<Int, Int>>()

        for (word in editedString) {
            if (word.isEmpty()) continue
            var startIndex = completeString.indexOf(word)
            while (startIndex >= 0) {
                styleRanges.add(startIndex to startIndex + word.length)
                startIndex = completeString.indexOf(word, startIndex + 1)
            }
        }

        val annotatedStr = buildAnnotatedString {
            var index = 0

            while (index < completeString.length) {
                val styleRange = styleRanges.find { it.first == index }

                if (styleRange != null) {
                    withStyle(style = SpanStyle(color = color)) {
                        append(completeString.substring(styleRange.first, styleRange.second))
                    }
                    index = styleRange.second
                } else {
                    append(completeString[index])
                    index++
                }
            }
        }

        annotatedString = annotatedStr
        return this
    }

    fun spannableToAnnotatedString(spannableString : Spannable): AnnotatedString {
        return buildAnnotatedString {
            append(spannableString) // basic text

            // copy span styles (bold, italic, etc.)
            spannableString.getSpans(0, length, CharacterStyle::class.java).forEach { span ->
                val start = spannableString.getSpanStart(span)
                val end = spannableString.getSpanEnd(span)

                when (span) {
                    is StyleSpan -> {
                        when (span.style) {
                            Typeface.BOLD -> addStyle(SpanStyle(fontWeight = FontWeight.Bold), start, end)
                            Typeface.ITALIC -> addStyle(SpanStyle(fontStyle = FontStyle.Italic), start, end)
                        }
                    }
                    is ForegroundColorSpan -> {
                        addStyle(SpanStyle(color = Color(span.foregroundColor)), start, end)
                    }
                    // add more span conversions if needed
                }
            }
        }
    }

    @Composable
    fun build(): AnnotatedString {
        return annotatedString
    }
}