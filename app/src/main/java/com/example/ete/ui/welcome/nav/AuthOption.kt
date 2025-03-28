package com.example.ete.ui.welcome.nav

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ete.R
import com.example.ete.theme.gray
import com.example.ete.theme.white

@Composable
@Preview(showBackground = true)
fun Preview() {
    AuthOption(
        0,
        stringResource(R.string.continue_with_email),
        painterResource(R.drawable.ic_email)
    )
}

@Composable
fun AuthOption(top: Int = 0, title: String, icon: Painter, onClick: () -> Unit = {}) {
    Row(
        modifier = Modifier.run {
            fillMaxWidth()
                .padding(top = if (top != 0) top.dp else 0.dp)
                .padding(horizontal = 24.dp)
                .shadow(6.dp, shape = RoundedCornerShape(18.dp), spotColor = gray)
                .background(white)
                .border(1.dp, color = gray, shape = RoundedCornerShape(18.dp))
                .padding(vertical = 12.dp)
                .clickable(
                    interactionSource = MutableInteractionSource(),
                    indication = null
                ) {
                    onClick()
                }
        }
    ) {

        Image(
            painter = icon,
            contentDescription = null,
            modifier = Modifier.padding(start = 67.dp)
        )

        Text(
            text = title,
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(horizontal = 14.dp),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.titleMedium
        )
    }
}