package com.example.ete.di.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.ete.R
import com.example.ete.data.bean.country.CountryBean
import com.example.ete.di.MyApplication
import com.example.ete.theme.black
import com.example.ete.theme.black_10
import com.example.ete.theme.grayV2
import com.example.ete.theme.grayV2_10
import com.example.ete.theme.grayV2_12
import com.example.ete.theme.white
import com.example.ete.ui.view.RowCountry
import com.example.ete.util.AppUtils

@Composable
@Preview(showBackground = true)
fun ShowCountryDialog(
    isCodeVisible: Boolean = false,
    onClick: (CountryBean) -> Unit = {},
    onDismiss: () -> Unit = {},
) {

    val search = remember { mutableStateOf("") }
    val countryList = if (isCodeVisible) {
        ArrayList(AppUtils.getCountryListWithCode())
    } else {
        MyApplication.instance?.getCountryList() ?: ArrayList()
    }
    val filteredList = countryList.filter {
        it.title.orEmpty().trim().contains(search.value.trim(), ignoreCase = true)
    }

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
        ) {
            Text(
                text = stringResource(R.string.select_country),
                style = MaterialTheme.typography.headlineSmall,
                color = black,
                fontSize = 24.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp)
                    .padding(horizontal = 24.dp),
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .padding(top = 10.dp)
                    .border(1.dp, grayV2_12, shape = RoundedCornerShape(10.dp))
                    .background(color = grayV2_10, shape = RoundedCornerShape(10.dp))
                    .padding(horizontal = 14.dp, vertical = 9.dp),
                contentAlignment = Alignment.CenterStart
            ) {

                Icon(
                    painter = painterResource(R.drawable.ic_search),
                    contentDescription = null,
                    modifier = Modifier.size(18.dp),
                )

                if (search.value.isEmpty()) {
                    Text(
                        text = stringResource(R.string.search_country),
                        color = grayV2,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(start = 28.dp)
                    )
                }

                BasicTextField(
                    value = search.value,
                    onValueChange = { search.value = it },
                    maxLines = 1,
                    textStyle = MaterialTheme.typography.bodyLarge.copy(color = black),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 28.dp)
                )
            }

            LazyColumn(
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .aspectRatio(1f / 1f),
                contentPadding = PaddingValues(vertical = 5.dp)
            ) {
                items(filteredList.toList()) { country ->
                    RowCountry(
                        country,
                        isCodeVisible,
                        onClick = {
                            onClick(country)
                        }
                    )
                }
            }
        }
    }
}