package com.example.ete.di

import android.app.Application
import android.content.Context
import android.content.Intent
import androidx.lifecycle.viewModelScope
import com.example.ete.data.Constant.PrefsKeys.AUTH_DATA
import com.example.ete.data.Constant.PrefsKeys.USER_DATA
import com.example.ete.data.EndPoint.DropDown.COUNTRY_LIST
import com.example.ete.data.EndPoint.DropDown.DROP_DOWN_LIST
import com.example.ete.data.bean.Authentication
import com.example.ete.data.bean.user.UserBean
import com.example.ete.data.remote.ApiRepositoryImpl
import com.example.ete.data.remote.helper.NetworkErrorHandler
import com.example.ete.data.sqlite.SqliteHelper
import com.example.ete.di.dagger.AppComponent
import com.example.ete.di.dagger.DaggerAppComponent
import com.example.ete.di.viewmodel.BaseViewModel
import com.example.ete.ui.main.MainActivity
import com.example.ete.util.Prefs
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class MyApplication : Application() {

    private lateinit var appComponent: AppComponent

    lateinit var networkErrorHandler: NetworkErrorHandler

    @Inject
    lateinit var apiRepoImpl: ApiRepositoryImpl

    companion object {
        var instance: MyApplication? = null
            private set

        fun applicationContext(): Context {
            return instance!!.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        appComponent = DaggerAppComponent.builder().build()
        appComponent.inject(myApplication = this)

        //SQLite
        SqliteHelper.initDatabaseInstance(this)
    }

    //Restart App
    fun restartApp() {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val intent = Intent(instance, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    //Get User detail
    fun getUserData(): UserBean {
        return if (Prefs.contains(USER_DATA))
            Gson().fromJson(Prefs.getString(USER_DATA, ""), object : TypeToken<UserBean>() {}.type)
        else
            UserBean()
    }

    //Get Authentication detail
    fun getAuthData(): Authentication {
        return if (Prefs.contains(AUTH_DATA))
            Gson().fromJson(Prefs.getString(AUTH_DATA, ""), object : TypeToken<Authentication>() {}.type)
        else
            Authentication()
    }


    //Call Drop Down Country API Call
    fun callGetCountryList(vm: BaseViewModel) {
        vm.viewModelScope.launch {
            apiRepoImpl.getCountryList().collect {
                Prefs.putString(COUNTRY_LIST, Gson().toJson(it.data?.data))
            }
        }
    }

    //Call Drop Down API Call
    fun callGetDropDownList(vm: BaseViewModel) {

        vm.viewModelScope.launch {
            apiRepoImpl.getDropDownList().collect {
                Prefs.putString(DROP_DOWN_LIST, Gson().toJson(it.data?.data))
            }
        }
    }
}