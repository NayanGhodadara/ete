package com.example.ete.ui.view

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ete.R
import com.example.ete.data.bean.dropdown.DropDownBean
import com.example.ete.theme.black
import com.example.ete.theme.whiteV2


@Composable
@Preview(showBackground = true)
fun RowSetting(
    dropDownBean: DropDownBean? = null,
    canHideDivider: Boolean = false,
    onToggleClick: (DropDownBean) -> Unit = {},
    onClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                onClick()
            }
    ) {

        Text(
            text = dropDownBean?.title.orEmpty(),
            style = MaterialTheme.typography.titleLarge,
            fontSize = 14.sp,
            color = black,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
                .padding(start = 14.dp, end = 60.dp)
        )

        Log.e("TAG",":::view recreate")

        if (dropDownBean?.isToggle?.not() == true)
            Image(
                painter = painterResource(R.drawable.ic_arrow_right),
                contentDescription = stringResource(R.string.app_name),
                modifier = Modifier
                    .size(35.dp)
                    .align(Alignment.CenterEnd)
                    .padding(end = 14.dp)
            )
        else
            Image(
                painter = if (dropDownBean?.isToggleSelected == true)
                    painterResource(R.drawable.ic_selected_toggle)
                else
                    painterResource(R.drawable.ic_toggle),
                contentDescription = stringResource(R.string.app_name),
                modifier = Modifier
                    .size(60.dp)
                    .align(Alignment.CenterEnd)
                    .padding(end = 14.dp)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) {
                        dropDownBean?.copy(isToggleSelected = !dropDownBean.isToggleSelected)?.let { onToggleClick(it) }
                    }
            )
    }

    if (canHideDivider.not())
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(whiteV2)
        )
}