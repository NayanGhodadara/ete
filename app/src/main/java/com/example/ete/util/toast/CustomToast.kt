package com.example.ete.util.toast

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ete.data.bean.ServerError
import com.example.ete.theme.black
import com.example.ete.theme.warningColor
import com.example.ete.theme.white
import kotlinx.coroutines.delay

@Composable
@Preview(showSystemUi = true)
fun PreviewToast() {
    CustomToast(ServerError("error", warningColor), onDismiss = {})
}

@Composable
fun CustomToast(serverError: ServerError, onDismiss: () -> Unit = {}) {
    var isVisible by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        delay(2000) // Toast duration
        isVisible = false
        delay(500) // Allow animation to complete
        onDismiss()
    }

    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = WindowInsets.statusBars.asPaddingValues().calculateTopPadding())
                .background(serverError.color, shape = RoundedCornerShape(10.dp))
                .border(0.5.dp, black, shape = RoundedCornerShape(10.dp))
                .pointerInput(Unit) {
                    detectHorizontalDragGestures { _, dragAmount ->
                        if (kotlin.math.abs(dragAmount) > 50) {
                            isVisible = false
                            onDismiss()
                        }
                    }
                }
                .pointerInput(Unit) {
                    detectVerticalDragGestures { _, dragAmount ->
                        if (dragAmount < -50) {
                            isVisible = false
                            onDismiss()
                        }
                    }
                },
            contentAlignment = Alignment.TopStart
        ) {
            Text(
                text = serverError.message,
                modifier = Modifier.padding(16.dp),
                maxLines = 5,
                color = white,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}