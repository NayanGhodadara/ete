package com.example.ete.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ete.R
import com.example.ete.theme.black
import com.example.ete.theme.black_10
import com.example.ete.theme.white

@Composable
@Preview(showBackground = true)
fun PreviewHeaderView() {
    HeaderView("title", isBackShow = true, isHelpShow = true, isMoreOptionShow = false)
}

@Composable
fun HeaderView(
    title: String,
    isBackShow: Boolean = false,
    isHelpShow: Boolean = false,
    isMoreOptionShow: Boolean = false,
    onBackClick: () -> Unit = {},
    onMoreOptionClick: () -> Unit = {},
    onHelpClick: () -> Unit = {},
) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(14.dp, spotColor = black_10, shape = RoundedCornerShape(bottomStart = 10.dp, bottomEnd = 10.dp))
            .clip(RoundedCornerShape(bottomStart = 10.dp, bottomEnd = 10.dp))
            .background(white)
            .drawBehind {
                val strokeWidth = 1.dp.toPx()
                drawLine(
                    color = black_10,
                    start = Offset(0f, size.height),
                    end = Offset(size.width, size.height),
                    strokeWidth = strokeWidth
                )
            }
    ){

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

        Text(
            text = title,
            style = MaterialTheme.typography.headlineSmall,
            color = black,
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
        )

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
}