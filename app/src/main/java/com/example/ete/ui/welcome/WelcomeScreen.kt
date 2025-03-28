package com.example.ete.ui.welcome

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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.ete.R
import com.example.ete.data.Constant.IntentObject.INTENT_IS_PHONE_AUTH
import com.example.ete.data.Constant.Screen.LOGIN
import com.example.ete.theme.white
import com.example.ete.ui.welcome.nav.AuthActivityVM
import com.example.ete.ui.welcome.nav.AuthOption

@Preview(showSystemUi = true)
@Composable
fun PreviewWelcome() {
    WelcomeScreen(null)
}

@Composable
fun WelcomeScreen(vm: AuthActivityVM? = null, navController: NavController? = null) {

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

                }
            )
        }
    }
}