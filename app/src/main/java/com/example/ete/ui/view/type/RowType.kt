package com.example.ete.ui.view.type

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ete.R
import com.example.ete.data.bean.dropdown.DropDownBean
import com.example.ete.theme.black
import com.example.ete.theme.grayV2_20

@Composable
@Preview(showBackground = true)
fun TypeRow(
    dropDownBean: DropDownBean? = null,
    selectedType: (DropDownBean) -> Unit = {},
    isDividerHide: Boolean = false
) {
    Column {

        Row(
            modifier = Modifier
                .padding(top = 12.dp)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
                    dropDownBean?.let { selectedType(it) }
                }
        ) {
            Image(
                painter = if (dropDownBean?.isSelected == true) {
                    painterResource(R.drawable.ic_selected_radio)
                } else {
                    painterResource(R.drawable.ic_radio)
                },
                contentDescription = stringResource(R.string.app_name),
                modifier = Modifier.size(20.dp),
            )
            Text(
                text = dropDownBean?.title.orEmpty(),
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 16.sp,
                color = black,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 12.dp)
            )
        }

        if (isDividerHide.not()) {
            Spacer(
                modifier = Modifier
                    .padding(top = 10.dp)
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(grayV2_20),
            )
        }
    }
}