package com.example.ete.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.ete.R
import com.example.ete.data.Constant.MainScreen.ADD
import com.example.ete.data.Constant.MainScreen.HOME
import com.example.ete.data.Constant.MainScreen.MY_JOURNAL
import com.example.ete.data.Constant.MainScreen.PROFILE
import com.example.ete.data.Constant.MainScreen.SEARCH
import com.example.ete.theme.EteTheme
import com.example.ete.theme.black
import com.example.ete.theme.grayV2
import com.example.ete.theme.white
import com.example.ete.ui.main.home.HomeScreen
import com.example.ete.ui.main.journal.JournalScreen
import com.example.ete.ui.main.profile.ProfileScreen
import com.example.ete.ui.main.search.SearchScreen

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
        val currentBackStackEntry by navController.currentBackStackEntryAsState()
        var selectedTab by remember { mutableStateOf(HOME) }

        Scaffold { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(white)
                    .padding(paddingValues)
            ) {
                NavHost(navController, HOME.name) {
                    composable(HOME.name) {
                        HomeScreen(navController)
                    }
                    composable(SEARCH.name) {
                        SearchScreen(navController)
                    }
                    composable(MY_JOURNAL.name) {
                        JournalScreen(navController)
                    }
                    composable(PROFILE.name) {
                        ProfileScreen(navController)
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(white)
                        .padding(horizontal = 16.dp)
                        .align(Alignment.BottomCenter),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    //HOME
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                            .padding(bottom = 10.dp)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) {
                                selectedTab = HOME
                            }
                    ) {
                        if (selectedTab == HOME) {
                            Spacer(
                                modifier = Modifier
                                    .height(5.dp)
                                    .fillMaxWidth()
                                    .background(black, shape = RoundedCornerShape(bottomEnd = 8.dp, bottomStart = 8.dp))
                                    .padding(horizontal = 4.dp)
                            )
                        }

                        Image(
                            painter = if (selectedTab == HOME) painterResource(R.drawable.ic_selected_home) else painterResource(R.drawable.ic_home),
                            contentDescription = stringResource(R.string.app_name),
                            modifier = Modifier
                                .padding(top = if (selectedTab == HOME) 10.dp else 15.dp)
                                .align(Alignment.CenterHorizontally),
                        )
                        Text(
                            text = stringResource(R.string.home),
                            style = MaterialTheme.typography.titleLarge,
                            color = if (selectedTab == HOME) black else grayV2,
                            fontSize = 10.sp,
                            modifier = Modifier
                                .padding(top = 4.dp)
                                .align(Alignment.CenterHorizontally)
                        )
                    }

                    //SEARCH
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                            .padding(bottom = 10.dp)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) {
                                selectedTab = SEARCH
                            }
                    ) {
                        if (selectedTab == SEARCH) {
                            Spacer(
                                modifier = Modifier
                                    .height(5.dp)
                                    .fillMaxWidth()
                                    .background(black, shape = RoundedCornerShape(bottomEnd = 8.dp, bottomStart = 8.dp))
                                    .padding(horizontal = 4.dp)
                            )
                        }

                        Image(
                            painter = if (selectedTab == SEARCH) painterResource(R.drawable.ic_selected_search) else painterResource(R.drawable.ic_search),
                            contentDescription = stringResource(R.string.app_name),
                            modifier = Modifier
                                .padding(top = if (selectedTab == SEARCH) 10.dp else 15.dp)
                                .align(Alignment.CenterHorizontally)
                        )
                        Text(
                            text = stringResource(R.string.search),
                            style = MaterialTheme.typography.titleLarge,
                            color = if (selectedTab == SEARCH) black else grayV2,
                            fontSize = 10.sp,
                            modifier = Modifier
                                .padding(top = 4.dp)
                                .align(Alignment.CenterHorizontally)
                        )
                    }

                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(R.drawable.ic_add),
                            contentDescription = null,
                            modifier = Modifier
                                .background(
                                    color = black,
                                    shape = RoundedCornerShape(360.dp)
                                )
                                .padding(11.dp)
                        )
                    }


                    //MY_JOURNAL
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                            .padding(bottom = 10.dp)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) {
                                selectedTab = MY_JOURNAL
                            }
                    ) {
                        if (selectedTab == MY_JOURNAL) {
                            Spacer(
                                modifier = Modifier
                                    .height(5.dp)
                                    .fillMaxWidth()
                                    .background(black, shape = RoundedCornerShape(bottomEnd = 8.dp, bottomStart = 8.dp))
                                    .padding(horizontal = 4.dp)
                            )
                        }

                        Image(
                            painter = if (selectedTab == MY_JOURNAL) painterResource(R.drawable.ic_selected_tab_journal) else painterResource(R.drawable.ic_tab_journal),
                            contentDescription = stringResource(R.string.app_name),
                            modifier = Modifier
                                .padding(top = if (selectedTab == MY_JOURNAL) 10.dp else 15.dp)
                                .align(Alignment.CenterHorizontally)
                        )
                        Text(
                            text = stringResource(R.string.my_journal),
                            style = MaterialTheme.typography.titleLarge,
                            color = if (selectedTab == MY_JOURNAL) black else grayV2,
                            fontSize = 10.sp,
                            modifier = Modifier
                                .padding(top = 4.dp)
                                .align(Alignment.CenterHorizontally)
                        )
                    }

                    //PROFILE
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                            .padding(bottom = 10.dp)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) {
                                selectedTab = PROFILE
                            }
                    ) {
                        if (selectedTab == PROFILE) {
                            Spacer(
                                modifier = Modifier
                                    .height(5.dp)
                                    .fillMaxWidth()
                                    .background(black, shape = RoundedCornerShape(bottomEnd = 8.dp, bottomStart = 8.dp))
                                    .padding(horizontal = 4.dp)
                            )
                        }

                        Image(
                            painter = if (selectedTab == PROFILE) painterResource(R.drawable.ic_selected_profile) else painterResource(R.drawable.ic_profile),
                            contentDescription = stringResource(R.string.app_name),
                            modifier = Modifier
                                .padding(top = if (selectedTab == PROFILE) 10.dp else 15.dp)
                                .align(Alignment.CenterHorizontally)
                        )
                        Text(
                            text = stringResource(R.string.profile),
                            style = MaterialTheme.typography.titleLarge,
                            color = if (selectedTab == PROFILE) black else {
                                grayV2
                            },
                            fontSize = 10.sp,
                            modifier = Modifier
                                .padding(top = 4.dp)
                                .align(Alignment.CenterHorizontally)
                        )
                    }
                }
            }
        }

        LaunchedEffect(selectedTab) {
            when (selectedTab) {
                HOME -> {
                    navController.navigate(HOME.name)
                }

                SEARCH -> {
                    navController.navigate(SEARCH.name)
                }

                ADD -> {

                }

                MY_JOURNAL -> {
                    navController.navigate(MY_JOURNAL.name)
                }

                PROFILE -> {
                    navController.navigate(PROFILE.name)
                }
            }
        }

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (currentBackStackEntry?.destination?.route == HOME.name) {
                    finish()
                } else {
                    selectedTab = HOME
                    navController.navigate(HOME.name) {
                        popUpTo(HOME.name) {
                            inclusive = true
                        }
                    }
                }
            }
        })
    }
}
