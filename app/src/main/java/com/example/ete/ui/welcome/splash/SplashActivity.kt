package com.example.ete.ui.welcome.splash

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ete.R
import com.example.ete.data.Constant
import com.example.ete.data.Constant.ForceUpdate.FORCE_UPDATE
import com.example.ete.data.Constant.ForceUpdate.OPTIONAL_UPDATE
import com.example.ete.data.Constant.ForceUpdate.UP_TO_DATE
import com.example.ete.data.Constant.IntentObject.INTENT_IS_FIRST_TIME
import com.example.ete.data.Constant.PrefsKeys.USER_DATA
import com.example.ete.data.remote.helper.Status
import com.example.ete.di.MyApplication
import com.example.ete.theme.EteTheme
import com.example.ete.ui.main.MainActivity
import com.example.ete.ui.welcome.nav.AuthActivity
import com.example.ete.util.Prefs
import com.google.gson.Gson

@SuppressLint("CustomSplashScreen")
class SplashActivity : ComponentActivity() {

    private val vm: SplashActivityVM by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            EteTheme {
                SplashScreen(this, vm)
            }
        }

        //Check App Version API Observer
        vm.obrCheckAppVersion.observe(this) {
            when (it.status) {
                Status.LOADING -> {}

                Status.SUCCESS -> {
                    when (it.data?.data?.status) {
                        UP_TO_DATE -> {
                            nextScreen(this, vm)
                        }

                        FORCE_UPDATE -> {
                            //showForceUpdateDialog(true)
                        }

                        OPTIONAL_UPDATE -> {
                            //showForceUpdateDialog(false)
                        }
                    }
                }

                Status.ERROR -> {
                    nextScreen(this, vm)
                }

                Status.WARN -> {
                    nextScreen(this, vm)
                }
            }
        }

        //Get User Api Observer
        vm.obrGetUser.observe(this) {
            when (it.status) {
                Status.LOADING -> {}

                Status.SUCCESS -> {
                    Prefs.putString(USER_DATA, Gson().toJson(it.data?.data))
                    if (MyApplication.instance?.getUserData()?.isAccountRestricted == true) {
                        //Start suspend Activity
                        finish()
                    } else {
                        if (MyApplication.instance?.getUserData()?.isFirstTime == true) {
                            //Create profile
                            startActivity(
                                Intent(this, AuthActivity::class.java).apply {
                                    putExtra(INTENT_IS_FIRST_TIME, true)
                                }
                            )
                            finish()
                        } else {
                            startActivity(Intent(this, MainActivity::class.java))
                            finish()
                        }
                    }
                }

                Status.ERROR -> {
                    startActivity(Intent(this, AuthActivity::class.java))
                    finish()
                }

                Status.WARN -> {
                    startActivity(Intent(this, AuthActivity::class.java))
                    finish()
                }
            }
        }

        //Init
        MyApplication.instance?.callGetCountryList(vm)
        MyApplication.instance?.callGetDropDownList(vm)
    }
}

@Composable
@Preview(showSystemUi = true)
fun Preview() {
    SplashScreen(LocalContext.current, SplashActivityVM())
}

@Composable
fun SplashScreen(context: Context, vm: SplashActivityVM) {

    RequestPushNotificationPermission(context, vm)

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_app_logo_full),
            contentDescription = stringResource(R.string.app_name),
            modifier = Modifier.size(200.dp),
        )
    }
}

fun nextScreen(activity: Activity, vm: SplashActivityVM) {
    if (MyApplication.instance?.getUserData()?.id == null) {
        activity.startActivity(Intent(activity, AuthActivity::class.java))
        activity.finish()
    } else {
        vm.callGetUserApi()
    }
}

@Composable
fun RequestPushNotificationPermission(context: Context, vm: SplashActivityVM) {

    val pushNotificationPermissionLauncher = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        if (isGranted) {
            vm.callCheckAppVersionAPI(context)
        }
    }

    SideEffect {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            pushNotificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        } else {
            vm.callCheckAppVersionAPI(context)
        }
    }
}