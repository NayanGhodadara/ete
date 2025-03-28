package com.example.ete.ui.welcome.createAccount

import android.app.Activity
import android.content.Intent
import android.webkit.URLUtil
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.example.ete.R
import com.example.ete.data.Constant.Gender.FEMALE
import com.example.ete.data.Constant.Gender.MALE
import com.example.ete.data.Constant.Gender.OTHER
import com.example.ete.di.dialog.ShowCountryDialog
import com.example.ete.di.dialog.ShowDateDialog
import com.example.ete.theme.black
import com.example.ete.theme.grayV2
import com.example.ete.theme.grayV2_10
import com.example.ete.theme.grayV2_12
import com.example.ete.theme.red
import com.example.ete.theme.successColor
import com.example.ete.theme.warningColor
import com.example.ete.theme.white
import com.example.ete.ui.main.MainActivity
import com.example.ete.ui.welcome.nav.AuthActivityVM
import com.example.ete.util.CustomBuildAnnotatedString
import kotlinx.coroutines.launch

@Preview(showSystemUi = true)
@Composable
fun PreviewCreateAccount() {
    CreateAccountScreen(null)
}

@Composable
fun CreateAccountScreen(vm: AuthActivityVM? = null, navController: NavController? = null) {
    val snackBarHostState = remember { SnackbarHostState() }
    val rememberCoroutineScope = rememberCoroutineScope()
    val snackBarColor = remember { mutableStateOf(successColor) }

    val context = LocalContext.current

    /**
     *  Field
     * **/
    val nameField = remember { mutableStateOf("") }
    val nameErrorMsg = remember { mutableStateOf("") }
    val usernameField = remember { mutableStateOf("") }
    val usernameErrorMsg = remember { mutableStateOf("") }
    val ganderRdo = remember { mutableStateOf(MALE) }
    val linkField = remember { mutableStateOf("") }
    val linkErrorMsg = remember { mutableStateOf("") }
    val dobField = remember { mutableStateOf("") }
    val countryField = remember { mutableStateOf("") }
    val countryErrorMsg = remember { mutableStateOf("") }
    val bioField = remember { mutableStateOf("") }
    var selectedDateInMillis by remember { mutableLongStateOf(System.currentTimeMillis()) }

    /**
     * Dialog
     * **/
    val showCountryDialog = remember { mutableStateOf(false) }
    var showDatePicker by remember { mutableStateOf(false) }

    fun checkValidation(onSuccess: () -> Unit) {
        if (nameField.value.trim().isEmpty()) {
            nameErrorMsg.value = context.getString(R.string.enter_name)
        } else {
            nameErrorMsg.value = ""
        }

        if (usernameField.value.trim().isEmpty()) {
            usernameErrorMsg.value = context.getString(R.string.please_enter_username)
        } else {
            usernameErrorMsg.value = ""
        }

        if (countryField.value.trim().isEmpty()) {
            countryErrorMsg.value = context.getString(R.string.please_select_country)
        } else {
            countryErrorMsg.value = ""
        }

        if (linkField.value.trim().isNotEmpty() && !URLUtil.isValidUrl(linkField.value.trim())) {
            linkErrorMsg.value = context.getString(R.string.please_enter_valid_link)
        } else {
            linkErrorMsg.value = ""
        }

        if (dobField.value.isEmpty()) {
            rememberCoroutineScope.launch {
                snackBarColor.value = warningColor
                snackBarHostState.showSnackbar(
                    message = "Please select date of birth",
                    duration = SnackbarDuration.Short,
                )
            }
        }
    }

    Scaffold(
        snackbarHost = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .zIndex(2f),
                contentAlignment = Alignment.TopCenter
            ) {
                SnackbarHost(snackBarHostState) { data ->
                    Snackbar(
                        snackbarData = data,
                        containerColor = snackBarColor.value,
                        contentColor = white,
                        actionColor = black,
                    )
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(white)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
            ) {

                /** Profile pic **/
                Text(
                    text = stringResource(R.string.create_your_profile),
                    style = MaterialTheme.typography.headlineSmall,
                    color = black,
                    fontSize = 26.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 36.dp)
                        .padding(horizontal = 24.dp),
                    textAlign = TextAlign.Center
                )

                Image(
                    painter = painterResource(R.drawable.ic_profile_placeholder),
                    contentDescription = null,
                    Modifier
                        .padding(top = 34.dp)
                        .align(Alignment.CenterHorizontally)
                        .size(90.dp)
                )

                Text(
                    text = CustomBuildAnnotatedString.instance.getReqString(
                        stringResource(R.string.add_profile_picture)
                    ),
                    style = MaterialTheme.typography.bodyLarge,
                    color = black,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp)
                        .padding(horizontal = 24.dp)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) {

                        },
                    textAlign = TextAlign.Center
                )

                /** Name Fields **/
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(top = 21.dp)
                ) {
                    Text(
                        text = CustomBuildAnnotatedString.instance.getReqString(
                            stringResource(R.string.name)
                        ),
                        style = MaterialTheme.typography.bodyLarge,
                        color = black,
                        fontSize = 12.sp,
                        modifier = Modifier
                            .weight(0.35f)
                            .padding(start = 24.dp, end = 19.dp)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) {

                            },
                        textAlign = TextAlign.Start
                    )

                    Box(
                        modifier = Modifier
                            .weight(0.65f)
                            .padding(end = 24.dp)
                            .border(
                                1.dp, if (nameErrorMsg.value.isNotEmpty()) {
                                    red
                                } else {
                                    grayV2_12
                                }, shape = RoundedCornerShape(10.dp)
                            )
                            .background(color = grayV2_10, shape = RoundedCornerShape(9.dp))
                            .padding(horizontal = 16.dp, vertical = 10.dp),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        if (nameField.value.isEmpty()) {
                            Text(
                                text = stringResource(R.string.enter_name),
                                color = grayV2,
                                fontSize = 12.sp,
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                        BasicTextField(
                            value = nameField.value,
                            onValueChange = { nameField.value = it },
                            maxLines = 1,
                            textStyle = MaterialTheme.typography.bodyLarge.copy(color = black),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Email,
                                imeAction = ImeAction.Next
                            ),
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }

                Row {
                    Spacer(
                        modifier = Modifier.weight(0.35f)
                    )
                    Text(
                        text = nameErrorMsg.value,
                        style = MaterialTheme.typography.bodyLarge,
                        color = red,
                        fontSize = 10.sp,
                        modifier = Modifier
                            .weight(0.65f)
                            .padding(start = 2.dp, end = 24.dp),
                        textAlign = TextAlign.Start
                    )
                }

                /** Username Fields **/
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(top = 5.dp)
                ) {
                    Text(
                        text = CustomBuildAnnotatedString.instance.getReqString(
                            stringResource(R.string.username)
                        ),
                        style = MaterialTheme.typography.bodyLarge,
                        color = black,
                        fontSize = 12.sp,
                        modifier = Modifier
                            .weight(0.35f)
                            .padding(start = 24.dp, end = 19.dp)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) {

                            },
                        textAlign = TextAlign.Start
                    )

                    Box(
                        modifier = Modifier
                            .weight(0.65f)
                            .padding(end = 24.dp)
                            .border(
                                1.dp, if (usernameErrorMsg.value.isNotEmpty()) {
                                    red
                                } else {
                                    grayV2_12
                                }, shape = RoundedCornerShape(10.dp)
                            )
                            .background(color = grayV2_10, shape = RoundedCornerShape(9.dp))
                            .padding(horizontal = 16.dp, vertical = 10.dp),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        if (usernameField.value.isEmpty()) {
                            Text(
                                text = stringResource(R.string.enter_username),
                                color = grayV2,
                                fontSize = 12.sp,
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                        BasicTextField(
                            value = usernameField.value,
                            onValueChange = { usernameField.value = it },
                            maxLines = 1,
                            textStyle = MaterialTheme.typography.bodyLarge.copy(color = black),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Email, imeAction = ImeAction.Next
                            ),
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }

                Row {
                    Spacer(
                        modifier = Modifier.weight(0.35f)
                    )
                    Text(
                        text = usernameErrorMsg.value,
                        style = MaterialTheme.typography.bodyLarge,
                        color = red,
                        fontSize = 10.sp,
                        modifier = Modifier
                            .weight(0.65f)
                            .padding(start = 2.dp, end = 24.dp),
                        textAlign = TextAlign.Start
                    )
                }

                /** Dob Fields **/
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(top = 5.dp)
                ) {
                    Text(
                        text = CustomBuildAnnotatedString.instance.getReqString(
                            stringResource(R.string.date_of_birth)
                        ),
                        style = MaterialTheme.typography.bodyLarge,
                        color = black,
                        fontSize = 12.sp,
                        modifier = Modifier
                            .weight(0.35f)
                            .padding(start = 24.dp, end = 19.dp)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) {

                            },
                        textAlign = TextAlign.Start
                    )

                    Box(
                        modifier = Modifier
                            .weight(0.65f)
                            .padding(end = 24.dp)
                            .border(
                                1.dp, grayV2_12, shape = RoundedCornerShape(10.dp)
                            )
                            .background(color = grayV2_10, shape = RoundedCornerShape(9.dp))
                            .padding(horizontal = 16.dp, vertical = 10.dp)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) {
                                if (showDatePicker.not()) {
                                    showDatePicker = true
                                }
                            },
                        contentAlignment = Alignment.CenterStart
                    ) {
                        if (dobField.value.isEmpty()) {
                            Text(
                                text = stringResource(R.string.enter_date_of_birth),
                                color = grayV2,
                                style = MaterialTheme.typography.bodyLarge,
                                maxLines = 1,
                                fontSize = 12.sp,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(end = 24.dp)
                            )
                        }

                        BasicTextField(
                            value = dobField.value,
                            onValueChange = { dobField.value = it },
                            maxLines = 1,
                            enabled = false,
                            textStyle = MaterialTheme.typography.bodyLarge.copy(color = black),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(end = 24.dp)
                        )


                        if (showDatePicker) {
                            ShowDateDialog(selectedDateInMillis, onDateSelect = { newDate, formatedDate ->
                                if (newDate == null) {
                                    showDatePicker = false
                                    return@ShowDateDialog
                                }

                                selectedDateInMillis = newDate
                                dobField.value = formatedDate
                                showDatePicker = false
                            })
                        }

                        // Calendar Icon
                        Image(
                            painter = painterResource(id = R.drawable.ic_date),
                            contentDescription = "Calendar Icon",
                            modifier = Modifier
                                .size(24.dp)
                                .align(Alignment.CenterEnd)
                        )
                    }
                }


                /** Gender Fields **/
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(top = 18.dp)
                ) {
                    Text(
                        text = stringResource(R.string.gender),
                        style = MaterialTheme.typography.bodyLarge,
                        color = black,
                        fontSize = 12.sp,
                        modifier = Modifier
                            .weight(0.35f)
                            .padding(start = 24.dp, end = 19.dp)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) {

                            },
                        textAlign = TextAlign.Start
                    )

                    Row(
                        modifier = Modifier
                            .weight(0.65f),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        listOf(MALE, FEMALE, OTHER).forEachIndexed { index, gender ->
                            RadioButton(
                                selected = ganderRdo.value == gender,
                                onClick = {
                                    ganderRdo.value = gender
                                },
                                modifier = Modifier
                                    .size(20.dp),
                                colors = RadioButtonDefaults.colors(
                                    unselectedColor = black,
                                    selectedColor = black
                                )
                            )

                            Text(
                                text = gender,
                                style = MaterialTheme.typography.bodyLarge,
                                color = black,
                                fontSize = 12.sp,
                                textAlign = TextAlign.Center,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier
                                    .wrapContentWidth()
                                    .padding(start = 6.dp, end = 18.dp)
                            )
                        }
                    }
                }

                /** Link Fields **/
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(top = 18.dp)
                ) {
                    Text(
                        text = stringResource(R.string.link),
                        style = MaterialTheme.typography.bodyLarge,
                        color = black,
                        fontSize = 12.sp,
                        modifier = Modifier
                            .weight(0.35f)
                            .padding(start = 24.dp, end = 19.dp)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) {

                            },
                        textAlign = TextAlign.Start
                    )

                    Box(
                        modifier = Modifier
                            .weight(0.65f)
                            .padding(end = 24.dp)
                            .border(
                                1.dp, if (linkErrorMsg.value.isNotEmpty()) {
                                    red
                                } else {
                                    grayV2_12
                                }, shape = RoundedCornerShape(10.dp)
                            )
                            .background(color = grayV2_10, shape = RoundedCornerShape(9.dp))
                            .padding(horizontal = 16.dp, vertical = 10.dp),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        if (linkField.value.isEmpty()) {
                            Text(
                                text = stringResource(R.string.enter_link),
                                color = grayV2,
                                fontSize = 12.sp,
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                        BasicTextField(
                            value = linkField.value,
                            onValueChange = { linkField.value = it },
                            maxLines = 1,
                            textStyle = MaterialTheme.typography.bodyLarge.copy(color = black),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Uri, imeAction = ImeAction.Next
                            ),
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }

                Row {
                    Spacer(
                        modifier = Modifier.weight(0.35f)
                    )
                    Text(
                        text = linkErrorMsg.value,
                        style = MaterialTheme.typography.bodyLarge,
                        color = red,
                        fontSize = 10.sp,
                        modifier = Modifier
                            .weight(0.65f)
                            .padding(start = 2.dp, end = 24.dp),
                        textAlign = TextAlign.Start
                    )
                }

                /** Country Fields **/
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(top = 5.dp)
                ) {
                    Text(
                        text = CustomBuildAnnotatedString.instance.getReqString(
                            stringResource(R.string.country)
                        ),
                        style = MaterialTheme.typography.bodyLarge,
                        color = black,
                        fontSize = 12.sp,
                        modifier = Modifier
                            .weight(0.35f)
                            .padding(start = 24.dp, end = 19.dp),
                        textAlign = TextAlign.Start
                    )

                    Box(
                        modifier = Modifier
                            .weight(0.65f)
                            .padding(end = 24.dp)
                            .border(
                                1.dp, if (countryErrorMsg.value.isNotEmpty()) red else grayV2_12, shape = RoundedCornerShape(10.dp)
                            )
                            .background(color = grayV2_10, shape = RoundedCornerShape(9.dp))
                            .padding(horizontal = 16.dp, vertical = 10.dp)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) {
                                if (showCountryDialog.value.not()) {
                                    showCountryDialog.value = true
                                }
                            },
                        contentAlignment = Alignment.CenterStart
                    ) {
                        if (countryField.value.isEmpty()) {
                            Text(
                                text = stringResource(R.string.select_country),
                                color = grayV2,
                                style = MaterialTheme.typography.bodyLarge,
                                maxLines = 1,
                                fontSize = 12.sp,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(end = 24.dp),
                            )
                        }
                        BasicTextField(
                            value = countryField.value,
                            onValueChange = { dobField.value = it },
                            maxLines = 1,
                            enabled = false,
                            textStyle = MaterialTheme.typography.bodyLarge.copy(color = black),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(end = 24.dp),
                        )

                        // Country Icon
                        Image(
                            painter = painterResource(id = R.drawable.ic_arrow_down),
                            contentDescription = "Calendar Icon",
                            modifier = Modifier
                                .size(24.dp)
                                .align(Alignment.CenterEnd)
                        )
                    }
                }

                if (showCountryDialog.value) {
                    ShowCountryDialog(
                        onClick = { countryBean ->
                            countryField.value = countryBean.title.orEmpty()
                            showCountryDialog.value = false
                        },
                        onDismiss = {
                            showCountryDialog.value = false
                        }
                    )
                }


                Row {
                    Spacer(
                        modifier = Modifier.weight(0.35f)
                    )
                    Text(
                        text = countryErrorMsg.value,
                        style = MaterialTheme.typography.bodyLarge,
                        color = red,
                        fontSize = 10.sp,
                        modifier = Modifier
                            .weight(0.65f)
                            .padding(start = 2.dp, end = 24.dp),
                        textAlign = TextAlign.Start
                    )
                }

                /** Bio Fields **/
                Row(
                    modifier = Modifier.padding(top = 5.dp)
                ) {
                    Text(
                        text = stringResource(R.string.bio),
                        style = MaterialTheme.typography.bodyLarge,
                        color = black,
                        fontSize = 12.sp,
                        modifier = Modifier
                            .weight(0.35f)
                            .padding(start = 24.dp, end = 19.dp)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) {

                            },
                        textAlign = TextAlign.Start
                    )

                    Box(
                        modifier = Modifier
                            .weight(0.65f)
                            .padding(end = 24.dp)
                            .border(1.dp, grayV2_12, shape = RoundedCornerShape(10.dp))
                            .background(color = grayV2_10, shape = RoundedCornerShape(9.dp))
                            .padding(horizontal = 16.dp, vertical = 10.dp),
                        contentAlignment = Alignment.TopStart
                    ) {
                        if (bioField.value.isEmpty()) {
                            Text(
                                text = stringResource(R.string.write_here),
                                color = grayV2,
                                fontSize = 12.sp,
                                style = MaterialTheme.typography.bodyLarge,
                            )
                        }
                        BasicTextField(
                            value = bioField.value,
                            onValueChange = { bioField.value = it },
                            maxLines = 4,
                            minLines = 4,
                            textStyle = MaterialTheme.typography.bodyLarge.copy(color = black),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }

            /** Create **/
            Text(
                text = stringResource(R.string.create), style = MaterialTheme.typography.headlineSmall, color = white,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp)
                    .padding(horizontal = 24.dp)
                    .background(black, shape = RoundedCornerShape(8.dp))
                    .padding(vertical = 12.dp)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) {
                        checkValidation(onSuccess = {
                            context.startActivity(Intent(context, MainActivity::class.java))
                            (context as Activity).finishAffinity()
                        })
                    },
                textAlign = TextAlign.Center,
            )
        }
    }
}