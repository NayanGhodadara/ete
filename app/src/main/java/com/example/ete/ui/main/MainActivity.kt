package com.example.ete.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.ete.theme.EteTheme

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
//                NavHost(navController, CREATE_ACCOUNT.name) {
//                    composable(WELCOME.name) {
//
//                    }
//                }
            }
        }
    }
}
