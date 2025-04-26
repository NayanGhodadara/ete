package com.example.ete.util.cookie

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.ete.theme.errorBorder
import com.example.ete.theme.errorColor
import com.example.ete.theme.infoBorder
import com.example.ete.theme.infoColor
import com.example.ete.theme.successBorder
import com.example.ete.theme.successColor
import com.example.ete.theme.warningBorder
import com.example.ete.theme.warningColor
import com.example.ete.theme.white
import kotlinx.coroutines.delay

enum class CookieBarType {
    ERROR,
    INFO,
    WARNING,
    SUCCESS
}

@Composable
fun CookieBar(message: String, cookieBarType: CookieBarType, onRemove: () -> Unit = {}) {
    var isVisible by remember { mutableStateOf(false) }

    val backgroundColor = when (cookieBarType) {
        CookieBarType.ERROR -> {
            errorColor
        }

        CookieBarType.INFO -> {
            infoColor
        }

        CookieBarType.WARNING -> {
            warningColor
        }

        CookieBarType.SUCCESS -> {
            successColor
        }
    }
    val borderColor = when (cookieBarType) {
        CookieBarType.ERROR -> {
            errorBorder
        }

        CookieBarType.INFO -> {
            infoBorder
        }

        CookieBarType.WARNING -> {
            warningBorder
        }

        CookieBarType.SUCCESS -> {
            successBorder
        }
    }

    LaunchedEffect(Unit) {
        isVisible = true
        delay(3000)
        isVisible = false
        onRemove()
    }

    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn(animationSpec = tween(800)) + // Slower fade-in
                slideInVertically(initialOffsetY = { -it }, animationSpec = tween(800)), // Slide in from top with a slower duration
        exit = slideOutVertically(targetOffsetY = { -it }, animationSpec = tween(600)) +
                fadeOut(animationSpec = tween(300, delayMillis = 300)), // Slide out to top
        modifier = Modifier.zIndex(1f)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 5.dp)
                .padding(horizontal = 18.dp)
                .background(backgroundColor, shape = RoundedCornerShape(10.dp))
                .border(1.dp, borderColor, shape = RoundedCornerShape(10.dp))
                .height(IntrinsicSize.Max),
        ) {
            Spacer(
                Modifier
                    .fillMaxHeight()
                    .width(0.dp)
                    .background(borderColor)
            )

            Spacer(Modifier.statusBarsPadding())

            Text(
                modifier = Modifier
                    .padding(start = 25.dp, end = 18.dp)
                    .padding(vertical = 20.dp),
                text = message,
                color = white,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                style = MaterialTheme.typography.headlineSmall
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CookieBarPreview() {
    Column {
        CookieBar("We use cookies for better experience", CookieBarType.ERROR)
        CookieBar("We use cookies for better experience", CookieBarType.INFO)
        CookieBar("We use cookies for better experience", CookieBarType.WARNING)
        CookieBar("We use cookies for better experience", CookieBarType.SUCCESS)
    }
}