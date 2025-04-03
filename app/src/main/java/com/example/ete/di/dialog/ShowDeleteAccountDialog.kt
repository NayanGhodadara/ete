package com.example.ete.di.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.ete.R
import com.example.ete.theme.black
import com.example.ete.theme.black_10
import com.example.ete.theme.grayV2
import com.example.ete.theme.grayV2_10
import com.example.ete.theme.grayV2_12
import com.example.ete.theme.red
import com.example.ete.theme.white
import com.example.ete.util.progress.AnimatedCircularProgress

@Composable
@Preview(showBackground = true)
fun ShowDeleteAccountDialog(
    onPositiveClick: () -> Unit = {},
    onNegativeClick: () -> Unit = {},
    showProgress: Boolean = false,
    onDismiss: () -> Unit = {},
) {

    val textField = remember { mutableStateOf("") }
    val errorMsg = remember { mutableStateOf("") }
    val context = LocalContext.current
    val keyboard = LocalSoftwareKeyboardController.current

    Dialog(
        onDismissRequest = {
            errorMsg.value = ""
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
                text = stringResource(R.string.delete_ask),
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
                text = stringResource(R.string.type_delete_to_confirm),
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 14.sp,
                color = grayV2,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp)
                    .padding(horizontal = 24.dp),
                textAlign = TextAlign.Center
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 6.dp)
                    .padding(horizontal = 15.dp)
                    .border(
                        1.dp, if (errorMsg.value.isNotEmpty()) {
                            red
                        } else {
                            grayV2_12
                        }, shape = RoundedCornerShape(10.dp)
                    )
                    .background(color = grayV2_10, shape = RoundedCornerShape(10.dp))
                    .padding(horizontal = 20.dp, vertical = 10.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                if (textField.value.isEmpty()) {
                    Text(
                        text = stringResource(R.string.enter_here),
                        color = grayV2,
                        textAlign = TextAlign.Center,
                        fontSize = 14.sp,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                BasicTextField(
                    value = textField.value,
                    onValueChange = { textField.value = it },
                    maxLines = 1,
                    textStyle = MaterialTheme.typography.bodyLarge.copy(
                        color = black,
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center
                    ),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            keyboard?.hide()
                            if (textField.value.trim().isNotEmpty() && textField.value.trim() == context.getString(R.string.delete)) {
                                onPositiveClick()
                            } else {
                                errorMsg.value = context.getString(R.string.please_enter_delete)
                            }
                        }
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Text(
                text = errorMsg.value,
                style = MaterialTheme.typography.bodyLarge,
                color = red,
                fontSize = 12.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 2.dp)
                    .padding(horizontal = 15.dp),
            )

            Row(
                modifier = Modifier
                    .padding(top = 6.dp)
                    .padding(horizontal = 15.dp)
            ) {
                Text(
                    text = stringResource(R.string.cancel),
                    style = MaterialTheme.typography.titleMedium,
                    color = black,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .padding(end = 8.dp)
                        .background(grayV2_10, shape = RoundedCornerShape(12.dp))
                        .border(1.dp, grayV2_12, shape = RoundedCornerShape(12.dp))
                        .padding(vertical = 14.dp)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) {
                            errorMsg.value = ""
                            onNegativeClick()
                        },
                    textAlign = TextAlign.Center
                )

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .padding(start = 8.dp)
                        .background(black, shape = RoundedCornerShape(12.dp))
                        .padding(vertical = 14.dp)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) {
                            if (textField.value.trim().isNotEmpty() && textField.value.trim() == context.getString(R.string.delete)) {
                                onPositiveClick()
                            } else {
                                errorMsg.value = context.getString(R.string.please_enter_delete)
                            }
                        }
                ) {
                    Text(
                        text = if (showProgress) "" else stringResource(R.string.confirm),
                        style = MaterialTheme.typography.titleMedium,
                        color = white,
                        modifier = Modifier
                            .align(Alignment.Center),
                    )

                    if (showProgress) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.Center),
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            AnimatedCircularProgress()
                        }
                    }
                }
            }
        }
    }
}