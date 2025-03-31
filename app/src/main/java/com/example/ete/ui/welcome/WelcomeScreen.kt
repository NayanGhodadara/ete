package com.example.ete.ui.welcome

import android.content.Context
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.ete.R
import com.example.ete.data.Constant.ApiObject.GOOGLE
import com.example.ete.data.Constant.IntentObject.INTENT_IS_PHONE_AUTH
import com.example.ete.data.Constant.Screen.LOGIN
import com.example.ete.data.bean.auth.AuthBean
import com.example.ete.data.remote.helper.Status
import com.example.ete.theme.white
import com.example.ete.ui.welcome.nav.AuthActivityVM
import com.example.ete.ui.welcome.nav.AuthOption
import com.example.ete.util.cookie.CookieBar
import com.example.ete.util.cookie.CookieBarType
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException

@Preview(showSystemUi = true)
@Composable
fun PreviewWelcome() {
    WelcomeScreen(null)
}

@Composable
fun WelcomeScreen(navController: NavController? = null) {
    val vm: AuthActivityVM = hiltViewModel()
    val context = LocalContext.current
    val obrSocialLogin = vm.obrSocialLogin.value

    val googleSignInLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try {
            val account: GoogleSignInAccount = task.getResult(ApiException::class.java)

            val authBean = AuthBean()
            authBean.provideType = GOOGLE
            authBean.token = account.idToken.orEmpty()
            authBean.email = account.email.orEmpty()
            authBean.fullName = account.displayName.orEmpty()
            authBean.profilePic = account.photoUrl.toString()
            vm.authBean = authBean
            vm.callSocialLoginApi()
        } catch (e: ApiException) {
            e.printStackTrace()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(white)
    ) {
        Image(
            painter = painterResource(R.drawable.ic_app_logo),
            contentDescription = null,
            modifier = Modifier
                .padding(top = 70.dp)
                .size(100.dp)
                .align(Alignment.CenterHorizontally)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 100.dp)
        ) {
            AuthOption(
                0,
                stringResource(R.string.continue_with_email),
                painterResource(R.drawable.ic_email),
                onClick = {
                    navController?.navigate("${LOGIN.name}?$INTENT_IS_PHONE_AUTH=false}")
                }
            )

            AuthOption(
                14,
                stringResource(R.string.continue_with_phone_no),
                painterResource(R.drawable.ic_phone),
                onClick = {
                    navController?.navigate("${LOGIN.name}?$INTENT_IS_PHONE_AUTH=true")
                }
            )

            AuthOption(
                14,
                stringResource(R.string.continue_with_google), painterResource(R.drawable.ic_google),
                onClick = {
                    val googleSignInClient = GoogleSignIn.getClient(context, getGoogleSignInOptions(context))
                    val signInIntent = googleSignInClient.signInIntent
                    googleSignInLauncher.launch(signInIntent)
                }
            )
        }
    }

    when (obrSocialLogin?.status) {
        Status.LOADING -> {
            vm.isLoading.value = true
        }

        Status.SUCCESS -> {
            vm.isLoading.value = false
        }

        Status.WARN -> {
            vm.isLoading.value = false
            CookieBar(obrSocialLogin.message.orEmpty(), CookieBarType.WARNING)
        }

        Status.ERROR -> {
            vm.isLoading.value = false
            CookieBar(obrSocialLogin.message.orEmpty(), CookieBarType.ERROR)
        }

        else -> {}
    }
}

private fun getGoogleSignInOptions(context : Context): GoogleSignInOptions {
    return GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(context.getString(R.string.google_oauth_client_id))
        .requestEmail()
        .build()
}