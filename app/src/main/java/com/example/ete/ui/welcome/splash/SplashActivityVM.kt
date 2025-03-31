package com.example.ete.ui.welcome.splash

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ete.data.Constant.ApiObject.ANDROID
import com.example.ete.data.Constant.ApiObject.PLATFORM
import com.example.ete.data.Constant.ApiObject.VERSION
import com.example.ete.data.bean.ApiResponse
import com.example.ete.data.bean.update.UpdateBean
import com.example.ete.data.bean.user.UserBean
import com.example.ete.data.remote.ApiRepositoryImpl
import com.example.ete.data.remote.helper.Resource
import com.example.ete.di.MyApplication
import com.example.ete.di.event.SingleLiveEvent
import com.example.ete.util.getAppVersionCode
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashActivityVM @Inject constructor(private val apiRepoImpl: ApiRepositoryImpl) : ViewModel() {

    fun callDropDown() {
        MyApplication.instance?.callGetCountryList(this, apiRepoImpl)
        MyApplication.instance?.callGetDropDownList(this, apiRepoImpl)
    }

    val obrCheckAppVersion = SingleLiveEvent<Resource<ApiResponse<UpdateBean>>>()
    fun callCheckAppVersionAPI(context: Context) = viewModelScope.launch {
        val rqMap = HashMap<String, Any>()
        rqMap[PLATFORM] = ANDROID
        rqMap[VERSION] = context.getAppVersionCode()


        apiRepoImpl.checkAppVersion(rqMap).collect {
            obrCheckAppVersion.value = it
        }
    }

    val obrGetUser = SingleLiveEvent<Resource<ApiResponse<UserBean>>>()
    fun callGetUserApi() = viewModelScope.launch {
        apiRepoImpl.getUserAsync().collect {
            obrGetUser.value = it
        }
    }
}