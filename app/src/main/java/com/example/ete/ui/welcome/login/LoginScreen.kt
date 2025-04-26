package com.example.ete.ui.welcome.login

import android.util.Patterns
import androidx.activity.compose.BackHandler
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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.ete.R
import com.example.ete.data.Constant.AuthScreen.LOGIN
import com.example.ete.data.Constant.AuthScreen.OTP
import com.example.ete.data.Constant.AuthScreen.WELCOME
import com.example.ete.data.Constant.IntentObject.INTENT_AUTH_BEAN
import com.example.ete.data.Constant.IntentObject.INTENT_IS_PHONE_AUTH
import com.example.ete.data.bean.auth.AuthBean
import com.example.ete.data.remote.helper.Status
import com.example.ete.di.dialog.ShowCountryDialog
import com.example.ete.theme.black
import com.example.ete.theme.grayV2
import com.example.ete.theme.grayV2_10
import com.example.ete.theme.grayV2_12
import com.example.ete.theme.red
import com.example.ete.theme.white
import com.example.ete.theme.whiteV2
import com.example.ete.ui.welcome.nav.AuthActivityVM
import com.example.ete.ui.welcome.nav.AuthOption
import com.example.ete.util.AppUtils
import com.example.ete.util.cookie.CookieBar
import com.example.ete.util.cookie.CookieBarType
import com.example.ete.util.progress.AnimatedCircularProgress
import com.example.ete.util.span.CustomBuildAnnotatedString


@Preview(showSystemUi = true)
@Composable
fun PreviewLogin() {
    LoginScreen(rememberNavController())
}

@Composable
fun LoginScreen(navController: NavController) {
    val vm: AuthActivityVM = hiltViewModel()

    BackHandler {
        navController.navigate(WELCOME.name) {
            popUpTo(LOGIN.name) { inclusive = false }
        }
    }
    val keyboardController = LocalSoftwareKeyboardController.current
    val context = LocalContext.current
    val obrSendOtp by vm.obrSendOtp

    /** Field **/
    val email = remember { mutableStateOf("") }
    val phone = remember { mutableStateOf("") }
    val isPhoneAuth = navController.currentBackStackEntry?.arguments?.getBoolean(INTENT_IS_PHONE_AUTH) ?: false
    val statePhone = remember { mutableStateOf(isPhoneAuth) }
    val errorMsg = remember { mutableStateOf("") }
    val countryField = remember { mutableStateOf(AppUtils.getDefaultCountry(context).getPlusCode()) }

    /**
     * Dialog
     * **/
    val showCountryDialog = remember { mutableStateOf(false) }

    /** Validation **/
    fun checkValidation(error: (String) -> Unit, success: () -> Unit) {
        if (statePhone.value) {
            when {
                phone.value.trim().isEmpty() -> {
                    error(context.getString(R.string.please_enter_number))
                }

                phone.value.trim().length < 5 -> {
                    error(context.getString(R.string.please_enter_valid_number))
                }

                else -> {
                    success()
                }
            }
        } else {
            when {
                email.value.trim().isEmpty() -> {
                    error(context.getString(R.string.please_enter_email))
                }

                !Patterns.EMAIL_ADDRESS.matcher(email.value.trim()).matches() -> {
                    error(context.getString(R.string.please_enter_valid_email))
                }

                else -> {
                    success()
                }
            }
        }
    }

    /** UI **/
    Box {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(white)
        ) {

            /** App icon **/
            Image(
                painter = painterResource(R.drawable.ic_app_logo),
                contentDescription = null,
                modifier = Modifier
                    .padding(top = 100.dp)
                    .size(100.dp)
                    .align(Alignment.CenterHorizontally)
            )

            /** Login input field **/
            Text(
                text = CustomBuildAnnotatedString.instance.getReqString(
                    if (statePhone.value) {
                        stringResource(R.string.phone_number)
                    } else {
                        stringResource(R.string.email)
                    }
                ),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .padding(top = 50.dp, start = 24.dp)
            )

            Row(
                modifier = Modifier.padding(top = 8.dp)
            ) {
                if (statePhone.value) {
                    Row(
                        modifier = Modifier
                            .padding(start = 24.dp)
                            .background(color = grayV2_10, shape = RoundedCornerShape(10.dp))
                            .border(1.dp, grayV2_12, shape = RoundedCornerShape(10.dp))
                            .padding(horizontal = 9.dp, vertical = 15.dp)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) {
                                if (showCountryDialog.value.not()) {
                                    showCountryDialog.value = true
                                }
                            },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = countryField.value,
                            style = MaterialTheme.typography.bodyLarge,
                            color = black,
                        )

                        Icon(
                            painter = painterResource(id = R.drawable.ic_arrow_down),
                            contentDescription = "Dropdown Icon",
                            modifier = Modifier
                                .padding(start = 4.dp)
                                .size(18.dp)
                        )
                    }

                    if (showCountryDialog.value) {
                        ShowCountryDialog(
                            isCodeVisible = true,
                            onClick = { newCountryBean ->
                                countryField.value = newCountryBean.getPlusCode()
                                showCountryDialog.value = false
                            },
                            onDismiss = {
                                showCountryDialog.value = false
                            }
                        )
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 10.dp, end = 24.dp)
                            .border(
                                1.dp, if (errorMsg.value.isNotEmpty()) {
                                    red
                                } else {
                                    grayV2_12
                                }, shape = RoundedCornerShape(10.dp)
                            )
                            .background(color = grayV2_10, shape = RoundedCornerShape(10.dp))
                            .padding(horizontal = 17.dp, vertical = 15.dp),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        if (phone.value.isEmpty()) {
                            Text(
                                text = stringResource(R.string.enter_number),
                                color = grayV2,
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                        BasicTextField(
                            value = phone.value,
                            onValueChange = { phone.value = it },
                            maxLines = 1,
                            textStyle = MaterialTheme.typography.bodyLarge.copy(color = black),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone, imeAction = ImeAction.Done),
                            keyboardActions = KeyboardActions(
                                onDone = {
                                    checkValidation(
                                        error = { errorMessage ->
                                            errorMsg.value = errorMessage
                                        },
                                        success = {
                                            vm.authBean.email = email.value.trim()
                                            vm.authBean.phone = phone.value.trim()
                                            vm.authBean.countryCode = countryField.value
                                            vm.callSendOtpAsync()
                                            keyboardController?.hide()
                                        }
                                    )
                                }),
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp)
                            .border(
                                1.dp, if (errorMsg.value.isNotEmpty()) {
                                    red
                                } else {
                                    grayV2_12
                                }, shape = RoundedCornerShape(10.dp)
                            )
                            .background(color = grayV2_10, shape = RoundedCornerShape(10.dp))
                            .padding(horizontal = 17.dp, vertical = 15.dp),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        if (email.value.isEmpty()) {
                            Text(
                                text = stringResource(R.string.enter_email),
                                color = grayV2,
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                        BasicTextField(
                            value = email.value,
                            onValueChange = { email.value = it },
                            maxLines = 1,
                            textStyle = MaterialTheme.typography.bodyLarge.copy(color = black),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email, imeAction = ImeAction.Done),
                            keyboardActions = KeyboardActions(onDone = {
                                checkValidation(
                                    error = { errorMessage ->
                                        errorMsg.value = errorMessage
                                    },
                                    success = {
                                        vm.authBean.email = email.value.trim()
                                        vm.authBean.phone = phone.value.trim()
                                        vm.authBean.countryCode = countryField.value
                                        vm.callSendOtpAsync()
                                        keyboardController?.hide()
                                    }
                                )
                            }),
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }

            /** Error **/
            Text(
                text = errorMsg.value,
                style = MaterialTheme.typography.bodyLarge,
                color = red,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 6.dp)
                    .padding(horizontal = 24.dp),
            )

            /** Login **/
            Box(
                Modifier
                    .padding(top = 18.dp)
                    .padding(horizontal = 24.dp)
            ) {
                Text(
                    text = if (vm.isLoading.value) "" else stringResource(R.string.login),
                    style = MaterialTheme.typography.headlineSmall,
                    color = white,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(black, shape = RoundedCornerShape(8.dp))
                        .padding(vertical = 12.dp)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) {
                            checkValidation(
                                error = { errorMessage ->
                                    errorMsg.value = errorMessage
                                },
                                success = {
                                    vm.authBean.email = email.value.trim()
                                    vm.authBean.phone = phone.value.trim()
                                    vm.authBean.countryCode = countryField.value
                                    vm.callSendOtpAsync()
                                    keyboardController?.hide()
                                }
                            )
                        },
                    textAlign = TextAlign.Center,
                )

                if (vm.isLoading.value) {
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

            /** Divider **/
            Row(
                modifier = Modifier.padding(vertical = 24.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .height(1.dp)
                        .padding(start = 42.dp, end = 10.dp)
                        .background(whiteV2)
                )
                Text(
                    text = stringResource(R.string.or_login_with),
                    style = MaterialTheme.typography.bodyLarge,
                    color = black
                )
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .height(1.dp)
                        .padding(start = 10.dp, end = 42.dp)
                        .background(whiteV2)
                )
            }

            /** Auth option **/
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {

                if (statePhone.value.not()) {
                    AuthOption(
                        0,
                        stringResource(R.string.continue_with_phone_no),
                        painterResource(R.drawable.ic_phone),
                        onClick = {
                            statePhone.value = true
                            email.value = ""
                            phone.value = ""
                            errorMsg.value = ""
                        }
                    )
                } else {
                    AuthOption(
                        0,
                        stringResource(R.string.continue_with_email),
                        painterResource(R.drawable.ic_email),
                        onClick = {
                            statePhone.value = false
                            email.value = ""
                            phone.value = ""
                            errorMsg.value = ""
                        }
                    )
                }

                AuthOption(
                    14,
                    stringResource(R.string.continue_with_google), painterResource(R.drawable.ic_google),
                    onClick = {
                        errorMsg.value = ""
                    }
                )
            }
        }
    }


    /** Observer **/
    when (obrSendOtp?.status) {
        Status.LOADING -> {
            vm.isLoading.value = true
        }

        Status.SUCCESS -> {
            vm.isLoading.value = false
            val authBean = AuthBean()
            authBean.countryCode = countryField.value
            authBean.phone = phone.value.trim()
            authBean.email = email.value.trim()
            authBean.successMessage = obrSendOtp?.data?.message.orEmpty()
            authBean.orderId = obrSendOtp?.data?.data?.orderId.orEmpty()

            navController.currentBackStackEntry
                ?.savedStateHandle
                ?.set(INTENT_AUTH_BEAN, authBean)

            navController.navigate(OTP.name) {
                popUpTo(LOGIN.name) { inclusive = true }
            }
        }

        Status.WARN -> {
            vm.isLoading.value = false
            CookieBar(obrSendOtp?.message.orEmpty(), CookieBarType.WARNING)
        }

        Status.ERROR -> {
            vm.isLoading.value = false
            CookieBar(obrSendOtp?.message.orEmpty(), CookieBarType.ERROR)
        }

        else -> {}
    }
}