package com.example.ete.ui.picker


import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.ete.R
import com.example.ete.di.MyApplication
import com.example.ete.theme.black
import com.example.ete.theme.grayV2
import com.example.ete.theme.grayV2_10
import com.example.ete.theme.grayV2_12
import com.example.ete.theme.white
import com.example.ete.ui.main.MainActivityVM
import com.example.ete.ui.view.header.HeaderView
import com.example.ete.ui.view.type.TypeRow
import com.example.ete.util.cookie.CookieBar
import com.example.ete.util.cookie.CookieBarType
import kotlinx.coroutines.delay

@Composable
@Preview(showSystemUi = true)
fun PostType(navController: NavController? = null) {
    val context = LocalContext.current
    val isPreview = LocalInspectionMode.current
    val vm: MainActivityVM? = if (isPreview) {
        null
    } else {
        hiltViewModel(context as ComponentActivity)
    }

    val otherText = remember { mutableStateOf("") }
    val showOther = remember { mutableStateOf(false) }
    val showSelectionError = remember { mutableStateOf(false) }
    val showOtherError = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        vm?.typeList?.clear()
        for (bean in MyApplication.instance?.getDropDownList()?.postType ?: arrayListOf()) {
            if (bean.title == vm?.selectedType?.value?.title) {
                bean.isSelected = true
                if (bean.title == context.getString(R.string.others)) {
                    showOther.value = true
                    otherText.value = vm.selectedType.value?.otherOption.orEmpty()
                }
            }
            vm?.typeList?.add(bean)
        }
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        HeaderView(
            title = stringResource(R.string.select_type),
            isBackShow = true,
            onBackClick = {
                navController?.popBackStack()
            }
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
                .padding(horizontal = 24.dp),
            contentPadding = PaddingValues(vertical = 12.dp)
        ) {

            itemsIndexed(vm?.typeList ?: arrayListOf()) { index, dropDown ->
                TypeRow(
                    dropDown, selectedType = {
                        vm?.typeList?.replaceAll { it.copy(isSelected = false) }
                        vm?.typeList[index] = dropDown.copy(isSelected = true)
                        showOther.value = vm?.typeList[index]?.title == context.getString(R.string.others) == true
                    },
                    isDividerHide = index == (vm?.typeList?.size ?: 0) - 1
                )
            }

            item {
                if (showOther.value) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp)
                            .border(1.dp, grayV2_12, shape = RoundedCornerShape(10.dp))
                            .background(color = grayV2_10, shape = RoundedCornerShape(9.dp))
                            .padding(horizontal = 16.dp, vertical = 18.dp),
                        contentAlignment = Alignment.TopStart
                    ) {
                        if (otherText.value.isEmpty()) {
                            Text(
                                text = stringResource(R.string.enter_here),
                                color = grayV2,
                                fontSize = 12.sp,
                                style = MaterialTheme.typography.bodyLarge,
                            )
                        }

                        BasicTextField(
                            value = otherText.value,
                            onValueChange = { otherText.value = it },
                            maxLines = 1,
                            textStyle = MaterialTheme.typography.bodyLarge.copy(color = black),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        }

        Box(
            Modifier
                .padding(top = 18.dp, bottom = 20.dp)
                .padding(horizontal = 24.dp),
        ) {
            Box {
                Text(
                    text = stringResource(R.string.save),
                    style = MaterialTheme.typography.headlineSmall,
                    color = white,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(black, shape = RoundedCornerShape(8.dp))
                        .padding(vertical = 12.dp)
                        .align(Alignment.BottomCenter)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) {
                            if (vm?.typeList?.any { it.isSelected } == true) {
                                if (vm.typeList.find { it.isSelected }?.title.equals(context.getString(R.string.others))) {
                                    if (otherText.value.trim().isNotEmpty()) {
                                        vm.typeList.find { it.isSelected }?.let {
                                            val dropDown = it
                                            dropDown.otherOption = otherText.value.trim()
                                            vm.selectedType.value = dropDown
                                            navController?.popBackStack()
                                        }
                                    } else {
                                        showOtherError.value = true
                                    }
                                } else {
                                    vm.typeList.find { it.isSelected }?.let {
                                        vm.selectedType.value = it
                                        navController?.popBackStack()
                                    }
                                }
                            } else {
                                showSelectionError.value = true
                            }
                        },
                    textAlign = TextAlign.Center,
                )
            }
        }
    }

    if (showSelectionError.value) {
        CookieBar(stringResource(R.string.please_select_any_type), CookieBarType.WARNING)
        LaunchedEffect(Unit) {
            delay(3000)
            showSelectionError.value = false
        }
    }

    if (showOtherError.value) {
        CookieBar(stringResource(R.string.please_enter_other_type), CookieBarType.WARNING)
        LaunchedEffect(Unit) {
            delay(3000)
            showOtherError.value = false
        }
    }
}