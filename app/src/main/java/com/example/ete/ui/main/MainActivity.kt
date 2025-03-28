package com.example.ete.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.ete.data.Constant.IntentObject.INTENT_EMAIL
import com.example.ete.data.Constant.IntentObject.INTENT_IS_PHONE_AUTH
import com.example.ete.data.Constant.IntentObject.INTENT_PHONE
import com.example.ete.data.Constant.Screen.CREATE_ACCOUNT
import com.example.ete.data.Constant.Screen.LOGIN
import com.example.ete.data.Constant.Screen.OTP
import com.example.ete.data.Constant.Screen.WELCOME
import com.example.ete.theme.EteTheme
import com.example.ete.ui.welcome.WelcomeScreen
import com.example.ete.ui.welcome.createAccount.CreateAccountScreen
import com.example.ete.ui.welcome.login.LoginScreen
import com.example.ete.ui.welcome.nav.AuthActivityVM
import com.example.ete.ui.welcome.otp.OtpScreen

class MainActivity : ComponentActivity() {

    private val vm: MainActivityVM by viewModels()

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

        Scaffold { paddingValues ->
            Box(modifier = Modifier.padding(paddingValues)) {
                NavHost(navController, CREATE_ACCOUNT.name) {
                    composable(WELCOME.name) {
                        
                    }
                }
            }
        }
    }
}
