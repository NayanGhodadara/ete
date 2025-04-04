package com.example.ete.di

import android.app.Application
import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ete.data.Constant.PrefsKeys.AUTH_DATA
import com.example.ete.data.Constant.PrefsKeys.USER_DATA
import com.example.ete.data.EndPoint.DropDown.COUNTRY_LIST
import com.example.ete.data.EndPoint.DropDown.DROP_DOWN_LIST
import com.example.ete.data.bean.Authentication
import com.example.ete.data.bean.country.CountryBean
import com.example.ete.data.bean.dropdown.DropDownBean
import com.example.ete.data.bean.dropdown.DropDownListBean
import com.example.ete.data.bean.user.UserBean
import com.example.ete.data.remote.ApiRepositoryImpl
import com.example.ete.data.remote.helper.NetworkErrorHandler
import com.example.ete.data.sqlite.SqliteHelper
import com.example.ete.ui.welcome.nav.AuthActivity
import com.example.ete.util.prefs.Prefs
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@HiltAndroidApp
class MyApplication : Application() {

    lateinit var networkErrorHandler: NetworkErrorHandler

    //For AWS token call
    var isApiCallRunning = false

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

        //SQLite
        SqliteHelper.initDatabaseInstance(this)
    }

    //Restart App
    fun restartApp() {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val intent = Intent(instance, AuthActivity::class.java)
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


    //Get Country detail
    fun getCountryList(): ArrayList<CountryBean> {
        return if (Prefs.contains(COUNTRY_LIST))
            Gson().fromJson(Prefs.getString(COUNTRY_LIST, ""), object : TypeToken<ArrayList<CountryBean>>() {}.type)
        else
            ArrayList()
    }


    //Get dropDown detail
    fun getDropDownList(): DropDownListBean {
        return if (Prefs.contains(DROP_DOWN_LIST))
            Gson().fromJson(Prefs.getString(DROP_DOWN_LIST, ""), object : TypeToken<DropDownListBean>() {}.type)
        else
            DropDownListBean()
    }


    //Call Drop Down Country API Call
    fun callGetCountryList(vm: ViewModel, apiRepoImpl: ApiRepositoryImpl) {
        vm.viewModelScope.launch {
            apiRepoImpl.getCountryList().collect {
                Prefs.putString(COUNTRY_LIST, Gson().toJson(it.data?.data))
            }
        }
    }

    //Call Drop Down API Call
    fun callGetDropDownList(vm: ViewModel, apiRepoImpl: ApiRepositoryImpl) {

        vm.viewModelScope.launch {
            apiRepoImpl.getDropDownList().collect {
                Prefs.putString(DROP_DOWN_LIST, Gson().toJson(it.data?.data))
            }
        }
    }
}