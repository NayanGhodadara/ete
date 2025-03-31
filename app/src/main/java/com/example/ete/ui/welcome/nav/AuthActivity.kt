package com.example.ete.ui.welcome.nav

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.ete.data.Constant.IntentObject.INTENT_IS_FIRST_TIME
import com.example.ete.data.Constant.IntentObject.INTENT_IS_PHONE_AUTH
import com.example.ete.data.Constant.Screen.CREATE_ACCOUNT
import com.example.ete.data.Constant.Screen.LOGIN
import com.example.ete.data.Constant.Screen.OTP
import com.example.ete.data.Constant.Screen.WELCOME
import com.example.ete.theme.EteTheme
import com.example.ete.ui.welcome.WelcomeScreen
import com.example.ete.ui.welcome.createAccount.CreateAccountScreen
import com.example.ete.ui.welcome.login.LoginScreen
import com.example.ete.ui.welcome.otp.OtpScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthActivity : ComponentActivity() {

    private val vm: AuthActivityVM by viewModels()

    private val isFirstTime by lazy {
        intent.getBooleanExtra(INTENT_IS_FIRST_TIME, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            EteTheme {
                MainView()
            }
        }
    }

    @Preview(showSystemUi = true)
    @Composable
    fun MainView() {
        val navController = rememberNavController()

        Box(modifier = Modifier.fillMaxSize()) {
            NavHost(navController, WELCOME.name) {
                composable(WELCOME.name) { WelcomeScreen(navController) }
                composable(
                    "${LOGIN.name}?$INTENT_IS_PHONE_AUTH={$INTENT_IS_PHONE_AUTH}",
                    arguments = listOf(navArgument(INTENT_IS_PHONE_AUTH) {
                        type = NavType.BoolType
                        nullable = false
                        defaultValue = false
                    })
                ) {
                    LoginScreen(navController)
                }
                composable(OTP.name) {
                    OtpScreen(navController)
                }

                composable(CREATE_ACCOUNT.name) { CreateAccountScreen(navController) }
            }

            if (isFirstTime) {
                navController.navigate(CREATE_ACCOUNT.name) {
                    popUpTo(CREATE_ACCOUNT.name) {
                        inclusive = true
                    }
                }
            }
        }


        if (vm.isLoading.value) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clickable(
                        enabled = false,
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) {}
            )
        }
    }
}