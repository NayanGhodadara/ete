package com.example.ete.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ete.data.bean.country.CountryBean
import com.example.ete.theme.black
import com.example.ete.theme.grayV2_10

@Composable
@Preview(showBackground = true)
fun RowCountry(
    countryBean: CountryBean? = null,
    isCodeVisible: Boolean = false,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                onClick()
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = countryBean?.title.orEmpty(),
            style = MaterialTheme.typography.bodyLarge,
            color = black,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            modifier = Modifier
                .weight(1f)
                .padding(end = 10.dp)
                .padding(vertical = 5.dp),
        )

        if (isCodeVisible) {
            Text(
                text = countryBean?.getPlusCode().orEmpty(),
                maxLines = 1,
                style = MaterialTheme.typography.bodyLarge,
                overflow = TextOverflow.Ellipsis,
                color = black,
            )
        }
    }


    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(grayV2_10)
    )
}
