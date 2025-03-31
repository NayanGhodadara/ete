package com.example.ete.di.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.ete.R
import com.example.ete.theme.black
import com.example.ete.theme.white

@Composable
@Preview(showBackground = true)
fun ShowImagePickerDialog(
    openGallery: () -> Unit = {},
    openCamera: () -> Unit = {},
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
                text = stringResource(R.string.select_option),
                style = MaterialTheme.typography.headlineSmall,
                fontSize = 24.sp,
                color = black,
                modifier = Modifier.padding(24.dp)
            )

            Text(
                text = stringResource(R.string.choose_from_gallery),
                style = MaterialTheme.typography.titleLarge,
                fontSize = 20.sp,
                color = black,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) {
                        openGallery()
                    }
            )

            Text(
                text = stringResource(R.string.camera),
                style = MaterialTheme.typography.titleLarge,
                fontSize = 20.sp,
                color = black,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .padding(top = 16.dp)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) {
                        openCamera()
                    }
            )

            Text(
                text = stringResource(R.string.cancel),
                style = MaterialTheme.typography.titleLarge,
                fontSize = 20.sp,
                color = black,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .padding(top = 16.dp)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) {
                        onDismiss()
                    }
            )
        }
    }
}