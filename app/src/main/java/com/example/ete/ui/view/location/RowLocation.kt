package com.example.ete.ui.view.location

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.res.ResourcesCompat
import androidx.wear.compose.material.MaterialTheme
import com.example.ete.theme.grayV2
import com.example.ete.theme.grayV2_10
import com.example.ete.theme.grayV2_12
import com.example.ete.util.span.CustomBuildAnnotatedString
import com.google.android.libraries.places.api.model.AutocompletePrediction


@Composable
@Preview(showBackground = true)
fun RowLocation(
    bean: AutocompletePrediction? = null,
    onClick: () -> Unit = {}
) {

    Row(
        modifier = Modifier
            .padding(horizontal = 3.dp)
            .background(grayV2_10, shape = RoundedCornerShape(6.dp))
            .border(1.dp, grayV2_12, shape = RoundedCornerShape(6.dp))
            .padding(vertical = 5.dp, horizontal = 8.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                onClick()
            }
    ) {
        bean?.getPrimaryText(null)?.let {
            Text(
                text = CustomBuildAnnotatedString().spannableToAnnotatedString(it),
                style = androidx.compose.material3.MaterialTheme.typography.titleLarge,
                fontSize = 12.sp,
                color = grayV2
            )
        }
    }
}