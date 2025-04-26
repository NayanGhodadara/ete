package com.example.ete.ui.main.profile.setting

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ete.R
import com.example.ete.data.EndPoint.DropDown.COUNTRY_LIST
import com.example.ete.data.EndPoint.DropDown.DROP_DOWN_LIST
import com.example.ete.data.bean.dropdown.DropDownBean
import com.example.ete.data.remote.helper.Status
import com.example.ete.di.MyApplication
import com.example.ete.di.dialog.ShowDeleteAccountDialog
import com.example.ete.di.dialog.ShowLogoutDialog
import com.example.ete.theme.EteTheme
import com.example.ete.theme.gray
import com.example.ete.theme.grayV2_12
import com.example.ete.theme.red
import com.example.ete.theme.white
import com.example.ete.ui.view.header.HeaderView
import com.example.ete.ui.view.RowSetting
import com.example.ete.ui.welcome.nav.AuthActivity
import com.example.ete.util.cookie.CookieBar
import com.example.ete.util.cookie.CookieBarType
import com.example.ete.util.prefs.Prefs
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay

@AndroidEntryPoint
class SettingActivity : ComponentActivity() {

    val vm: SettingActivityVM by viewModels()

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
        val context = LocalContext.current

        val showLogout = remember { mutableStateOf(false) }
        val showDeleteDialog = remember { mutableStateOf(false) }

        val showDialogProgress = remember { mutableStateOf(false) }
        val showError = remember { mutableStateOf("") }
        val showWarn = remember { mutableStateOf("") }

        val list = remember {
            mutableStateListOf(
                DropDownBean(1, context.getString(R.string.leaderboard)),
                DropDownBean(2, context.getString(R.string.notification), isToggle = true),
                DropDownBean(3, context.getString(R.string.blocked_user)),
                DropDownBean(4, context.getString(R.string.privacy_and_Policy)),
                DropDownBean(5, context.getString(R.string.help)),
                DropDownBean(6, context.getString(R.string.disable_account)),
                DropDownBean(7, context.getString(R.string.logout)),
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(with(LocalDensity.current) {
                        WindowInsets.systemBars.getTop(this).toDp()
                    })
                    .background(white)
            )

            HeaderView(
                title = stringResource(R.string.settings),
                isBackShow = true,
                onBackClick = {
                    finish()
                }
            )

            LazyColumn(
                modifier = Modifier
                    .padding(vertical = 24.dp, horizontal = 24.dp)
                    .shadow(10.dp, spotColor = grayV2_12, shape = RoundedCornerShape(10.dp))
                    .background(white, shape = RoundedCornerShape(10.dp))
                    .border(1.dp, gray, shape = RoundedCornerShape(10.dp))
            ) {
                itemsIndexed(list) { index, data ->
                    RowSetting(
                        data,
                        canHideDivider = list.size - 1 == index,
                        onToggleClick = { dropDownBean ->
                            list[index] = dropDownBean
                        },
                        onClick = {
                            when (data.id) {
                                7L -> {
                                    if (showLogout.value.not()) {
                                        showLogout.value = true
                                    }
                                }
                            }
                        })
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = stringResource(R.string.want_to_delete_your_account),
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 12.sp,
                maxLines = 1,
                textDecoration = TextDecoration.Underline,
                textAlign = TextAlign.Center,
                overflow = TextOverflow.Ellipsis,
                color = red,
                modifier = Modifier
                    .padding(bottom = 24.dp)
                    .align(Alignment.CenterHorizontally)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) {
                        if (showDeleteDialog.value.not()) {
                            showDeleteDialog.value = true
                        }
                    }
            )

            if (showDeleteDialog.value) {
                ShowDeleteAccountDialog(
                    showProgress = showDialogProgress.value,
                    onPositiveClick = {
                        vm.callDeleteAccountApi()
                    },
                    onNegativeClick = {
                        showDeleteDialog.value = false
                    },
                    onDismiss = {
                        showDeleteDialog.value = false
                    }
                )
            }

            if (showLogout.value) {
                ShowLogoutDialog(
                    showProgress = showDialogProgress.value,
                    onPositiveClick = {
                        vm.callSocialLoginApi(context)
                    },
                    onNegativeClick = {
                        showLogout.value = false
                    },
                    onDismiss = {
                        showLogout.value = false
                    }
                )
            }
        }

        vm.obrLogout.observe(context as SettingActivity) {
            when (it.status) {
                Status.SUCCESS -> {
                    showLogout.value = false
                    showDialogProgress.value = false

                    val countryList = MyApplication.instance?.getCountryList()
                    val dropDownList = MyApplication.instance?.getDropDownList()
                    Prefs.clear()
                    Prefs.putString(COUNTRY_LIST, Gson().toJson(countryList))
                    Prefs.putString(DROP_DOWN_LIST, Gson().toJson(dropDownList))

                    startActivity(Intent(context, AuthActivity::class.java))
                    finishAffinity()
                }

                Status.ERROR -> {
                    showDialogProgress.value = false
                }

                Status.WARN -> {
                    showDialogProgress.value = false
                }

                Status.LOADING -> {
                    showDialogProgress.value = true
                }

                Status.INIT -> {}
            }
        }

        vm.obrDeleteAccount.observe(context) {
            when (it.status) {
                Status.SUCCESS -> {
                    showDeleteDialog.value = false
                    showDialogProgress.value = false

                    val countryList = MyApplication.instance?.getCountryList()
                    val dropDownList = MyApplication.instance?.getDropDownList()
                    Prefs.clear()
                    Prefs.putString(COUNTRY_LIST, Gson().toJson(countryList))
                    Prefs.putString(DROP_DOWN_LIST, Gson().toJson(dropDownList))

                    startActivity(Intent(context, AuthActivity::class.java))
                    finishAffinity()
                }

                Status.ERROR -> {
                    showDialogProgress.value = false
                }

                Status.WARN -> {
                    showDialogProgress.value = false
                }

                Status.LOADING -> {
                    showDialogProgress.value = true
                }

                Status.INIT -> {}
            }
        }

        if (showWarn.value.isNotEmpty()) {
            CookieBar(showWarn.value, CookieBarType.WARNING)
            LaunchedEffect(Unit) {
                delay(3000)
                showWarn.value = ""
            }
        }
        if (showError.value.isNotEmpty()) {
            CookieBar(showError.value, CookieBarType.ERROR)
            LaunchedEffect(Unit) {
                delay(3000)
                showError.value = ""
            }
        }
    }
}
