package com.example.ete.ui.view.shimmer

import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.PathEasing
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ete.theme.whiteV2
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.placeholder
import com.google.accompanist.placeholder.shimmer

@Composable
@Preview(showBackground = true)
fun ShimmerPost() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp)
                .padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(
                modifier = Modifier
                    .size(45.dp)
                    .placeholder(
                        visible = true,
                        highlight = PlaceholderHighlight.shimmer(
                            highlightColor = Color.LightGray.copy(alpha = 1f),
                            animationSpec = infiniteRepeatable(
                                animation = tween(durationMillis = 1000, easing = LinearEasing)
                            )
                        ),
                        color = whiteV2,
                        shape = CircleShape
                    )
            )

            Column(
                Modifier
                    .padding(horizontal = 12.dp)
                    .weight(1f)
            ) {
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(20.dp)
                        .placeholder(
                            visible = true,
                            highlight = PlaceholderHighlight.shimmer(
                                highlightColor = Color.LightGray.copy(alpha = 1f),
                                animationSpec = infiniteRepeatable(
                                    animation = tween(durationMillis = 1000, easing = LinearEasing)
                                )
                            ),
                            color = whiteV2,
                            shape = RoundedCornerShape(5.dp)
                        )
                        .height(10.dp),
                )

                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(20.dp)
                        .padding(top = 2.dp)
                        .placeholder(
                            visible = true,
                            highlight = PlaceholderHighlight.shimmer(
                                highlightColor = Color.LightGray.copy(alpha = 1f),
                                animationSpec = infiniteRepeatable(
                                    animation = tween(durationMillis = 1000, easing = LinearEasing)
                                )
                            ),
                            color = whiteV2,
                            shape = RoundedCornerShape(5.dp)
                        )
                        .height(10.dp),
                )
            }

            Spacer(
                modifier = Modifier
                    .width(20.dp)
                    .height(35.dp)
                    .placeholder(
                        visible = true,
                        highlight = PlaceholderHighlight.shimmer(
                            highlightColor = Color.LightGray.copy(alpha = 1f),
                            animationSpec = infiniteRepeatable(
                                animation = tween(durationMillis = 1000, easing = LinearEasing)
                            )
                        ),
                        color = whiteV2,
                        shape = RoundedCornerShape(5.dp)
                    ),
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp)
                .aspectRatio(1f)
                .placeholder(
                    visible = true,
                    highlight = PlaceholderHighlight.shimmer(
                        highlightColor = Color.LightGray.copy(alpha = 1f),
                        animationSpec = infiniteRepeatable(
                            animation = tween(durationMillis = 1000, easing = LinearEasing)
                        )
                    ),
                    color = whiteV2,
                    shape = RectangleShape
                )
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp)
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {

                //Like
                Spacer(
                    modifier = Modifier
                        .size(25.dp)
                        .placeholder(
                            visible = true,
                            highlight = PlaceholderHighlight.shimmer(
                                highlightColor = Color.LightGray.copy(alpha = 1f),
                                animationSpec = infiniteRepeatable(
                                    animation = tween(durationMillis = 1000, easing = LinearEasing)
                                )
                            ),
                            color = whiteV2,
                            shape = RoundedCornerShape(5.dp)
                        )
                )

                Spacer(
                    modifier = Modifier
                        .padding(start = 10.dp)
                        .size(25.dp)
                        .placeholder(
                            visible = true,
                            highlight = PlaceholderHighlight.shimmer(
                                highlightColor = Color.LightGray.copy(alpha = 1f),
                                animationSpec = infiniteRepeatable(
                                    animation = tween(durationMillis = 1000, easing = LinearEasing)
                                )
                            ),
                            color = whiteV2,
                            shape = RoundedCornerShape(5.dp)
                        )
                )

                Spacer(
                    modifier = Modifier
                        .padding(start = 10.dp)
                        .size(25.dp)
                        .placeholder(
                            visible = true,
                            highlight = PlaceholderHighlight.shimmer(
                                highlightColor = Color.LightGray.copy(alpha = 1f),
                                animationSpec = infiniteRepeatable(
                                    animation = tween(durationMillis = 1000, easing = LinearEasing)
                                )
                            ),
                            color = whiteV2,
                            shape = RoundedCornerShape(5.dp)
                        )
                )
            }

            //Library and watchlist
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                Spacer(
                    modifier = Modifier
                        .padding(end = 10.dp)
                        .size(25.dp)
                        .placeholder(
                            visible = true,
                            highlight = PlaceholderHighlight.shimmer(
                                highlightColor = Color.LightGray.copy(alpha = 1f),
                                animationSpec = infiniteRepeatable(
                                    animation = tween(durationMillis = 1000, easing = LinearEasing)
                                )
                            ),
                            color = whiteV2,
                            shape = RoundedCornerShape(5.dp)
                        )
                )

                Spacer(
                    modifier = Modifier
                        .size(25.dp)
                        .placeholder(
                            visible = true,
                            highlight = PlaceholderHighlight.shimmer(
                                highlightColor = Color.LightGray.copy(alpha = 1f),
                                animationSpec = infiniteRepeatable(
                                    animation = tween(durationMillis = 1000, easing = LinearEasing)
                                )
                            ),
                            color = whiteV2,
                            shape = RoundedCornerShape(5.dp)
                        )
                )
            }
        }

        Spacer(
            modifier = Modifier
                .padding(horizontal = 12.dp)
                .padding(top = 12.dp)
                .fillMaxWidth()
                .height(20.dp)
                .placeholder(
                    visible = true,
                    highlight = PlaceholderHighlight.shimmer(
                        highlightColor = Color.LightGray.copy(alpha = 1f),
                        animationSpec = infiniteRepeatable(
                            animation = tween(durationMillis = 1000, easing = LinearEasing)
                        )
                    ),
                    color = whiteV2,
                    shape = RoundedCornerShape(5.dp)
                )
        )

        Spacer(
            modifier = Modifier
                .padding(horizontal = 12.dp)
                .padding(top = 4.dp)
                .fillMaxWidth()
                .height(20.dp)
                .placeholder(
                    visible = true,
                    highlight = PlaceholderHighlight.shimmer(
                        highlightColor = Color.LightGray.copy(alpha = 1f),
                        animationSpec = infiniteRepeatable(
                            animation = tween(durationMillis = 1000, easing = LinearEasing)
                        )
                    ),
                    color = whiteV2,
                    shape = RoundedCornerShape(5.dp)
                )
        )

        Spacer(
            modifier = Modifier
                .padding(top = 12.dp)
                .fillMaxWidth()
                .height(1.dp)
                .placeholder(
                    visible = true,
                    highlight = PlaceholderHighlight.shimmer(
                        highlightColor = Color.LightGray.copy(alpha = 1f),
                        animationSpec = infiniteRepeatable(
                            animation = tween(durationMillis = 1000, easing = LinearEasing)
                        )
                    ),
                    color = whiteV2,
                    shape = RoundedCornerShape(5.dp)
                )
        )
    }
}
