package com.example.ete.di.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.ete.R
import com.example.ete.theme.black
import com.example.ete.theme.black_10
import com.example.ete.theme.grayV2
import com.example.ete.theme.white
import com.example.ete.util.progress.AnimatedCircularProgress

@Composable
@Preview(showBackground = true)
fun ShowLogoutDialog(
    onPositiveClick: () -> Unit = {},
    onNegativeClick: () -> Unit = {},
    showProgress: Boolean = false,
    onDismiss: () -> Unit = {},
) {

    Dialog(
        onDismissRequest = {
            onDismiss()
        },
        properties = DialogProperties(
            dismissOnClickOutside = true,
            dismissOnBackPress = true
        ),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .background(white)
                .border(1.dp, color = black_10)
                .padding(bottom = 24.dp)
        ) {

            Text(
                text = stringResource(R.string.logout),
                style = MaterialTheme.typography.titleLarge,
                fontSize = 24.sp,
                color = black,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp)
                    .padding(horizontal = 24.dp),
                textAlign = TextAlign.Center
            )

            Text(
                text = stringResource(R.string.are_you_sure_you_want_to_logout_your_account),
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 14.sp,
                color = grayV2,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 6.dp)
                    .padding(horizontal = 24.dp),
                textAlign = TextAlign.Center
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .padding(top = 24.dp)
            ) {
                Text(
                    text = if (showProgress) "" else stringResource(R.string.confirm),
                    style = MaterialTheme.typography.titleLarge,
                    fontSize = 16.sp,
                    color = white,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(black, shape = RoundedCornerShape(10.dp))
                        .padding(vertical = 12.dp)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) {
                            onPositiveClick()
                        },
                    textAlign = TextAlign.Center
                )

                if (showProgress) {
                    Column(
                        modifier = Modifier.fillMaxWidth()
                            .align(Alignment.Center),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        AnimatedCircularProgress()
                    }
                }
            }

            Text(
                text = stringResource(R.string.go_back),
                style = MaterialTheme.typography.titleLarge,
                fontSize = 16.sp,
                color = black,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp)
                    .padding(horizontal = 24.dp)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) {
                        onNegativeClick()
                    },
                textAlign = TextAlign.Center
            )
        }
    }
}