package com.example.ete.ui.welcome.otp

import android.app.Activity
import android.content.Intent
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.ete.R
import com.example.ete.data.Constant.AuthScreen.CREATE_ACCOUNT
import com.example.ete.data.Constant.AuthScreen.LOGIN
import com.example.ete.data.Constant.AuthScreen.OTP
import com.example.ete.data.Constant.IntentObject.INTENT_AUTH_BEAN
import com.example.ete.data.Constant.PrefsKeys.AUTH_DATA
import com.example.ete.data.Constant.PrefsKeys.USER_DATA
import com.example.ete.data.bean.auth.AuthBean
import com.example.ete.data.remote.helper.Status
import com.example.ete.theme.black
import com.example.ete.theme.grayV2
import com.example.ete.theme.grayV2_10
import com.example.ete.theme.grayV2_12
import com.example.ete.theme.red
import com.example.ete.theme.white
import com.example.ete.ui.main.MainActivity
import com.example.ete.ui.welcome.nav.AuthActivityVM
import com.example.ete.util.cookie.CookieBar
import com.example.ete.util.cookie.CookieBarType
import com.example.ete.util.prefs.Prefs
import com.example.ete.util.progress.AnimatedCircularProgress
import com.example.ete.util.span.CustomBuildAnnotatedString
import com.google.gson.Gson
import kotlinx.coroutines.delay

@Preview(showSystemUi = true)
@Composable
fun PreviewOtp() {
    OtpScreen(rememberNavController())
}

@Composable
fun OtpScreen(navController: NavController) {
    val vm: AuthActivityVM = hiltViewModel()

    BackHandler {
        navController.navigate(LOGIN.name) {
            popUpTo(OTP.name) { inclusive = true }
        }
    }

    val obrSendOtp by vm.obrSendOtp.observeAsState()
    val obrVerify by vm.obrVerify.observeAsState()

    val authBean = navController.previousBackStackEntry
        ?.savedStateHandle
        ?.get<AuthBean>(INTENT_AUTH_BEAN)

    CookieBar(authBean?.successMessage.orEmpty(), CookieBarType.SUCCESS)

    val keyboardController = LocalSoftwareKeyboardController.current
    val context = LocalContext.current
    val activity = context as Activity

    val statePhone = authBean?.phone.orEmpty().isEmpty().not()
    var otpValue by remember { mutableStateOf("") }

    var canShowError by remember { mutableStateOf(false) }

    //Set Timer
    @Composable
    fun SetTimer(onResend: () -> Unit) {
        var currentTime by remember { mutableLongStateOf(60 * 1000) }
        var isRunning by remember { mutableStateOf(true) }

        LaunchedEffect(isRunning) {
            while (currentTime > 0L && isRunning) {
                delay(1000L)
                currentTime -= 1000L
            }
            isRunning = false
        }

        Text(
            text = if (currentTime <= 0L) {
                stringResource(R.string.resend)
            } else {
                stringResource(R.string.resend_code_in).format(currentTime / 1000)
            },
            style = MaterialTheme.typography.bodyLarge,
            color = if (currentTime <= 0L) {
                black
            } else {
                grayV2
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
                    if (currentTime <= 0L) {
                        currentTime = 20 * 1000L
                        isRunning = true
                        onResend()
                    }
                },
            textAlign = TextAlign.Center
        )
    }

    fun isValid(): Boolean {
        return otpValue.length == 6
    }

    //Set Phone Number and Email
    fun setEmailAndPhoneNumber(): String {
        try {
            val phoneString = authBean?.phone.orEmpty().trim()
            val emailString = authBean?.email.orEmpty().trim()
            if (statePhone) {
                val fist2Word: String = phoneString.substring(0, 2)
                val last2Word: String = phoneString.substring(phoneString.length - 2, phoneString.length)
                val phoneFormat = context.getString(R.string.phone_verification_code_sent_to).format(fist2Word, last2Word)
                return phoneFormat
            } else {
                val emailFormat: String
                when (emailString.substring(0, emailString.indexOf("@")).length) {
                    1 -> {
                        emailFormat = context.getString(R.string.email_verification_code_sent_to).format(emailString.substring(0, 1), emailString.substring(emailString.indexOf("@"), emailString.length))
                    }

                    2 -> {
                        emailFormat = context.getString(R.string.email_verification_code_sent_to).format(emailString.substring(0, 1), emailString.substring(emailString.indexOf("@") - 1, emailString.length))
                    }

                    3 -> {
                        emailFormat = context.getString(R.string.email_verification_code_sent_to).format(emailString.substring(0, 1), emailString.substring(emailString.indexOf("@") - 2, emailString.length))
                    }

                    else -> {
                        emailFormat = context.getString(R.string.email_verification_code_sent_to).format(emailString.substring(0, 2), emailString.substring(emailString.indexOf("@") - 2, emailString.length))
                    }
                }
                return emailFormat
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return ""
        }
    }

    /** UI **/
    @Composable
    fun CharView(index: Int) {
        val isFilled = index < otpValue.length
        val char = if (isFilled) otpValue[index].toString() else "0"
        val textColor = if (isFilled) black else grayV2

        Text(
            modifier = Modifier
                .border(
                    1.dp,
                    if (canShowError) {
                        red
                    } else {
                        grayV2_12
                    }, RoundedCornerShape(10.dp)
                )
                .background(grayV2_10, RoundedCornerShape(10.dp))
                .padding(horizontal = 21.dp, vertical = 16.dp),
            text = char,
            style = MaterialTheme.typography.bodyLarge,
            color = textColor,
            textAlign = TextAlign.Center
        )
    }

    @Composable
    fun OtpTextField(
        otpCount: Int = 6,
        onOtpTextChange: (String, Boolean) -> Unit,
        onDoneClick: () -> Unit
    ) {

        BasicTextField(
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .padding(top = 8.dp),
            value = TextFieldValue(otpValue, selection = TextRange(otpValue.length)),
            onValueChange = {
                if (it.text.length <= otpCount) {
                    onOtpTextChange.invoke(it.text, it.text.length == otpCount)
                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(onDone = {
                onDoneClick()
                if (otpValue.length == otpCount) {
                    keyboardController?.hide()

                    if (isValid()) {
                        canShowError = false
                        authBean?.otp = otpValue
                        vm.authBean = authBean ?: AuthBean()
                        vm.callVerifyOtp()
                        keyboardController?.hide()
                    } else {
                        canShowError = true
                    }
                }
            }),
            decorationBox = {
                Row(horizontalArrangement = Arrangement.Center) {
                    repeat(otpCount) { index ->
                        CharView(index)
                        Spacer(modifier = Modifier.width(5.dp))
                    }
                }
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(white)
    ) {
        /** Header **/
        Icon(
            painter = painterResource(R.drawable.ic_arrow_left),
            contentDescription = null,
            modifier = Modifier
                .padding(top = 14.dp, start = 24.dp)
                .size(24.dp)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
                    navController.popBackStack()
                }
        )

        Text(
            text = stringResource(R.string.verify_your_email),
            style = MaterialTheme.typography.headlineSmall,
            fontSize = 26.sp,
            color = black,
            modifier = Modifier
                .padding(top = 24.dp)
                .padding(horizontal = 24.dp)
        )
        Text(
            text = setEmailAndPhoneNumber(),
            style = MaterialTheme.typography.bodyLarge,
            fontSize = 16.sp,
            color = grayV2,
            modifier = Modifier
                .padding(top = 4.dp)
                .padding(horizontal = 24.dp)
        )

        Text(
            text = CustomBuildAnnotatedString.instance.getReqString(
                stringResource(R.string.otp)
            ),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .padding(top = 24.dp, start = 24.dp)
        )

        /** Input **/
        OtpTextField(
            onOtpTextChange = { value, _ ->
                otpValue = value
            },
            onDoneClick = {
                if (isValid()) {
                    canShowError = false
                    authBean?.otp = otpValue
                    vm.authBean = authBean ?: AuthBean()
                    vm.callVerifyOtp()
                    keyboardController?.hide()
                } else {
                    canShowError = true
                }
            }
        )

        if (canShowError) {
            Text(
                text = stringResource(R.string.please_enter_valid_otp),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                color = red,
                textAlign = TextAlign.Center
            )
        }

        /** Timer **/
        SetTimer(onResend = {
            vm.callSendOtpAsync()
        })

        /** Verify **/
        Box(
            Modifier
                .padding(top = 36.dp)
        ) {
            Text(
                text = if (vm.isLoading.value) "" else stringResource(R.string.verify),
                style = MaterialTheme.typography.headlineSmall,
                color = white,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .background(black, shape = RoundedCornerShape(8.dp))
                    .padding(vertical = 12.dp)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) {
                        if (isValid()) {
                            canShowError = false
                            authBean?.otp = otpValue
                            vm.authBean = authBean ?: AuthBean()
                            vm.callVerifyOtp()
                            keyboardController?.hide()
                        } else {
                            canShowError = true
                        }
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
    }

    /** Observer **/
    when (obrSendOtp?.status) {
        Status.LOADING -> {
            vm.isLoading.value = true
        }

        Status.SUCCESS -> {
            vm.isLoading.value = false
            authBean?.orderId = obrSendOtp?.data?.data?.orderId.orEmpty()
            CookieBar(obrSendOtp?.message.orEmpty(), CookieBarType.SUCCESS)
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

    when (obrVerify?.status) {
        Status.LOADING -> {
            vm.isLoading.value = true
        }

        Status.SUCCESS -> {
            vm.isLoading.value = false

            val userData = obrVerify?.data?.data
            Prefs.putString(USER_DATA, Gson().toJson(userData))
            Prefs.putString(AUTH_DATA, Gson().toJson(userData?.authentication))

            if (userData?.isAccountRestricted == true) {
                //startNewActivity(SuspendAccountActivity.newIntent(this))
            } else {
                if (userData?.isFirstTime == true) {
                    navController.navigate(CREATE_ACCOUNT.name) {
                        popUpTo(OTP.name) { inclusive = true }
                    }
                } else {
                    val intent = Intent(context, MainActivity::class.java)
                    context.startActivity(intent)
                    activity.finishAffinity()
                }
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
