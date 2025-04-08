package com.example.ete.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import com.example.ete.R
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.ete.data.Constant.PostType.BOOKMARK
import com.example.ete.data.bean.dropdown.DropDownBean
import com.example.ete.theme.black
import com.example.ete.theme.grayV2

@Composable
@Preview(showBackground = true)
fun ProfileTabs(
    dropDownBean: DropDownBean? = DropDownBean(title = "Home"), onClick: (DropDownBean) -> Unit = {}
) {
    var textWidth by remember { mutableIntStateOf(0) }

    ConstraintLayout(
        modifier = Modifier
            .wrapContentWidth()
            .height(30.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                onClick(dropDownBean?.copy(isSelected = true) ?: DropDownBean())
            }
    ) {
        val (title, image, divider) = createRefs()
        if (dropDownBean?.title.orEmpty().isEmpty()) {
            Image(
                painter = if (dropDownBean?.isSelected == true)
                    painterResource(R.drawable.ic_selected_view_all)
                else
                    painterResource(R.drawable.ic_view_all),
                contentDescription = stringResource(R.string.app_name),
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .onGloballyPositioned { coordinates ->
                        textWidth = coordinates.size.width
                    }
                    .constrainAs(image) {
                        top.linkTo(parent.top)
                        end.linkTo(parent.end)
                        start.linkTo(parent.start)
                    }
            )

            if (dropDownBean?.isSelected == true)
                Box(
                    modifier = Modifier
                        .height(2.dp)
                        .width(with(LocalDensity.current) { textWidth.toDp() })
                        .background(black, shape = RoundedCornerShape(5.dp))
                        .constrainAs(divider) {
                            bottom.linkTo(parent.bottom)
                            end.linkTo(parent.end)
                            start.linkTo(parent.start)
                        }
                )
        } else {
            Text(
                text = dropDownBean?.title.orEmpty(),
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 14.sp,
                color = if (dropDownBean?.isSelected == true) black else grayV2,
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .onGloballyPositioned { coordinates ->
                        textWidth = coordinates.size.width
                    }
                    .constrainAs(title) {
                        top.linkTo(parent.top)
                        end.linkTo(parent.end)
                        start.linkTo(parent.start)
                    }
            )
            if (dropDownBean?.isSelected == true)
                Box(
                    modifier = Modifier
                        .height(2.dp)
                        .width(with(LocalDensity.current) { textWidth.toDp() })
                        .background(black, shape = RoundedCornerShape(5.dp))
                        .constrainAs(divider) {
                            bottom.linkTo(parent.bottom)
                            end.linkTo(parent.end)
                            start.linkTo(parent.start)
                        }
                )
        }
    }
}