@file:Suppress("DEPRECATION")

package com.example.ete.ui.view.shimmer

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ete.theme.grayV2_10
import com.example.ete.theme.grayV2_12
import com.example.ete.theme.whiteV2
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.placeholder
import com.google.accompanist.placeholder.shimmer

@Composable
@Preview(showBackground = true)
fun ShimmerLibrary() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {

        Row {
            Spacer(
                modifier = Modifier
                    .size(70.dp)
                    .placeholder(
                        visible = true,
                        highlight = PlaceholderHighlight.shimmer(
                            highlightColor = Color.LightGray.copy(alpha = 1f),
                            animationSpec = infiniteRepeatable(
                                animation = tween(durationMillis = 1000, easing = LinearEasing)
                            )
                        ),
                        color = whiteV2,
                        shape = RoundedCornerShape(8.dp)
                    )
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(start = 8.dp)
                    .background(grayV2_10, shape = RoundedCornerShape(10.dp))
                    .border(1.dp, grayV2_12, shape = RoundedCornerShape(10.dp))
                    .padding(bottom = 8.dp)
            ) {
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                        .height(25.dp)
                        .padding(horizontal = 8.dp)
                        .placeholder(
                            visible = true,
                            highlight = PlaceholderHighlight.shimmer(
                                highlightColor = Color.LightGray.copy(alpha = 1f),
                                animationSpec = infiniteRepeatable(
                                    animation = tween(durationMillis = 1000, easing = LinearEasing)
                                )
                            ),
                            color = whiteV2,
                            shape = RoundedCornerShape(8.dp)
                        ),
                )

                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 2.dp)
                        .height(25.dp)
                        .padding(horizontal = 8.dp)
                        .placeholder(
                            visible = true,
                            highlight = PlaceholderHighlight.shimmer(
                                highlightColor = Color.LightGray.copy(alpha = 1f),
                                animationSpec = infiniteRepeatable(
                                    animation = tween(durationMillis = 1000, easing = LinearEasing)
                                )
                            ),
                            color = whiteV2,
                            shape = RoundedCornerShape(8.dp)
                        ),
                )
            }
        }
    }
}
