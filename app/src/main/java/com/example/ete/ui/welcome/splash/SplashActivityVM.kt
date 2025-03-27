package com.example.ete.ui.welcome.splash

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.example.ete.data.Constant.ApiObject.ANDROID
import com.example.ete.data.Constant.ApiObject.PLATFORM
import com.example.ete.data.Constant.ApiObject.VERSION
import com.example.ete.data.bean.ApiResponse
import com.example.ete.data.bean.update.UpdateBean
import com.example.ete.data.bean.user.UserBean
import com.example.ete.data.remote.helper.Resource
import com.example.ete.di.event.SingleLiveEvent
import com.example.ete.di.viewmodel.BaseViewModel
import com.example.ete.util.getAppVersionCode
import kotlinx.coroutines.launch

class SplashActivityVM : BaseViewModel() {

    val obrCheckAppVersion = SingleLiveEvent<Resource<ApiResponse<UpdateBean>>>()
    fun callCheckAppVersionAPI(context: Context) = viewModelScope.launch {
        val rqMap = HashMap<String, Any>()
        rqMap[PLATFORM] = ANDROID
        rqMap[VERSION] = context.getAppVersionCode()


        apiRepoImpl?.checkAppVersion(rqMap)?.collect {
            obrCheckAppVersion.value = it
        }
    }

    val obrGetUser = SingleLiveEvent<Resource<ApiResponse<UserBean>>>()
    fun callGetUserApi() = viewModelScope.launch {
        apiRepoImpl?.getUserAsync()?.collect {
            obrGetUser.value = it
        }
    }
}