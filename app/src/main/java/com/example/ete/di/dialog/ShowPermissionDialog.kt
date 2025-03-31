package com.example.ete.di.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.ete.R
import com.example.ete.theme.black
import com.example.ete.theme.grayV2
import com.example.ete.theme.grayV2_10
import com.example.ete.theme.grayV2_12
import com.example.ete.theme.white

@Composable
@Preview(showBackground = true)
fun ShowPermissionDialog(
    title: String = stringResource(R.string.app_name),
    desc: String = stringResource(R.string.app_name),
    onCancel: () -> Unit = {},
    onConfirm: () -> Unit = {},
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
                .background(white, shape = RoundedCornerShape(10.dp))
                .padding(bottom = 24.dp),
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineSmall,
                fontSize = 24.sp,
                color = black,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp)
                    .padding(top = 24.dp)
            )

            Text(
                text = desc,
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 14.sp,
                color = grayV2,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp)
                    .padding(top = 6.dp)
            )

            Row(
                modifier = Modifier
                    .padding(horizontal = 15.dp)
                    .padding(top = 36.dp)
            ) {
                Text(
                    text = stringResource(R.string.cancel),
                    style = MaterialTheme.typography.titleLarge,
                    fontSize = 16.sp,
                    color = black,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(end = 17.dp)
                        .background(grayV2_10, shape = RoundedCornerShape(10.dp))
                        .border(1.dp, grayV2_12, shape = RoundedCornerShape(10.dp))
                        .padding(vertical = 12.dp)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) {
                            onCancel()
                        },
                    textAlign = TextAlign.Center
                )

                Text(
                    text = stringResource(R.string.confirm),
                    style = MaterialTheme.typography.titleLarge,
                    fontSize = 16.sp,
                    color = white,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .background(black, shape = RoundedCornerShape(10.dp))
                        .padding(vertical = 12.dp)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) {
                            onConfirm()
                        },
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}