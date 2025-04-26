package com.example.ete.ui.view.like

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.ete.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

@Composable
fun LikeButtonWithFlyingHearts(
    isLiked: Boolean,
    onLikeClick: () -> Unit
) {
    val flyingLikes = remember { mutableStateListOf<FlyingLikeData>() }
    val scope = rememberCoroutineScope()
    var lastClickTime by remember { mutableLongStateOf(0L) }
    val previousLikeState = rememberUpdatedState(isLiked)

    Box(
        contentAlignment = Alignment.Center
    ) {
        // Base Like Button
        Image(
            painter = painterResource(
                if (isLiked) R.drawable.ic_like else R.drawable.ic_unlike
            ),
            contentDescription = null,
            modifier = Modifier
                .size(28.dp)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
                    val now = System.currentTimeMillis()

                    if (now - lastClickTime > 400) {
                        lastClickTime = now

                        val willBeLiked = !isLiked
                        onLikeClick()

                        if (!previousLikeState.value && willBeLiked) {
                            // Show flying hearts
                            scope.launch {
                                repeat(5) { index ->
                                    val id = Random.nextInt()
                                    val offsetX = (-30..30 step 10).shuffled().first()
                                    val scale = Random.nextFloat() * 0.5f + 1f

                                    flyingLikes.add(FlyingLikeData(id, offsetX, scale))

                                    // Remove after time
                                    launch {
                                        delay(1800L + index * 100L)
                                        flyingLikes.removeAll { it.id == id }
                                    }

                                    delay(120L)
                                }
                            }
                        }
                    }
                }
        )

        // Flying hearts overlay
        flyingLikes.forEach {
            FlyingLikeIcon(
                modifier = Modifier.offset {
                    IntOffset(it.offsetX.dp.roundToPx(), 0)
                },
                key = it.id,
                scale = it.scale
            )
        }
    }
}

data class FlyingLikeData(
    val id: Int,
    val offsetX: Int,
    val scale: Float
)

@Composable
fun FlyingLikeIcon(
    modifier: Modifier = Modifier,
    key: Int,
    scale: Float
) {
    val offsetY = remember { Animatable(0f) }
    val alpha = remember { Animatable(Random.nextFloat() * 0.5f + 0.5f) }
    val rotation = remember { Random.nextFloat() * 30f - 15f }

    LaunchedEffect(key) {
        launch {
            offsetY.animateTo(
                targetValue = -250f,
                animationSpec = tween(durationMillis = 1000, easing = FastOutSlowInEasing)
            )
        }
        launch {
            alpha.animateTo(
                targetValue = 0f,
                animationSpec = tween(durationMillis = 2000)
            )
        }
    }

    Image(
        painter = painterResource(R.drawable.ic_like),
        contentDescription = null,
        modifier = modifier
            .graphicsLayer {
                translationY = offsetY.value
                this.alpha = alpha.value
                scaleX = scale
                scaleY = scale
                rotationZ = rotation
            }
            .size(22.dp)
    )
}