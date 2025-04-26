package com.example.ete.ui.view.header

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ete.R
import com.example.ete.theme.black
import com.example.ete.theme.grayV2_12
import com.example.ete.theme.white

@Composable
@Preview(showBackground = true)
fun PreviewHeaderView() {
    HeaderView(title = "title", isBackShow = false, isHelpShow = false, isMoreOptionShow = false, canShowHomeHeader = true)
}

@Composable
fun HeaderView(
    title: String = "",
    isBackShow: Boolean = false,
    isHelpShow: Boolean = false,
    isMoreOptionShow: Boolean = false,
    canShowHomeHeader: Boolean = false,
    onBackClick: () -> Unit = {},
    onMoreOptionClick: () -> Unit = {},
    onHelpClick: () -> Unit = {},
) {

    Column {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(white)
                .padding(top = 12.dp)
        ) {
            if (canShowHomeHeader) {
                Image(
                    painter = painterResource(R.drawable.ic_app_logo),
                    contentDescription = null,
                    modifier = Modifier
                        .width(60.dp)
                        .padding(start = 24.dp)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) {
                            onBackClick()
                        }
                        .align(Alignment.CenterStart)
                )

                Row(
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(end = 24.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(end = 12.dp)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) {
                                onHelpClick()
                            },
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(R.drawable.ic_library),
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )

                        Text(
                            text = stringResource(R.string.library),
                            style = MaterialTheme.typography.bodyLarge.copy(
                                platformStyle = PlatformTextStyle(includeFontPadding = true)
                            ),
                            color = black,
                            fontSize = 10.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(top = 2.dp)
                        )
                    }

                    Column(
                        modifier = Modifier
                            .padding(end = 12.dp)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) {
                                onHelpClick()
                            },
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(R.drawable.ic_resource),
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )

                        Text(
                            text = stringResource(R.string.resources),
                            style = MaterialTheme.typography.bodyLarge.copy(
                                platformStyle = PlatformTextStyle(includeFontPadding = true)
                            ),
                            color = black,
                            fontSize = 10.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(top = 2.dp)
                        )
                    }

                    Column(
                        modifier = Modifier
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) {
                                onHelpClick()
                            },
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(R.drawable.ic_notification),
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )

                        Text(
                            text = stringResource(R.string.notification),
                            style = MaterialTheme.typography.bodyLarge.copy(
                                platformStyle = PlatformTextStyle(includeFontPadding = true)
                            ),
                            color = black,
                            fontSize = 10.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(top = 2.dp)
                        )
                    }

                }
            }


            if (isBackShow) {
                Image(
                    painter = painterResource(R.drawable.ic_arrow_left),
                    contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(start = 24.dp)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) {
                            onBackClick()
                        }
                )
            }

            if (canShowHomeHeader.not()) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineSmall,
                    color = black,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterStart),
                )
            }

            if (isMoreOptionShow) {
                Image(
                    painter = painterResource(R.drawable.ic_option),
                    contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(end = 24.dp)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) {
                            onMoreOptionClick()
                        }
                )
            }

            if (isHelpShow) {
                Column(
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(end = 24.dp)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) {
                            onHelpClick()
                        },
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(R.drawable.ic_help),
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )

                    Text(
                        text = stringResource(R.string.help),
                        style = MaterialTheme.typography.bodyLarge.copy(
                            platformStyle = PlatformTextStyle(includeFontPadding = true)
                        ),
                        color = black,
                        fontSize = 10.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(top = 2.dp)
                    )
                }
            }
        }

        Spacer(
            modifier = Modifier
                .padding(top = 10.dp)
                .fillMaxWidth()
                .height(1.dp)
                .background(grayV2_12)
        )
    }
}