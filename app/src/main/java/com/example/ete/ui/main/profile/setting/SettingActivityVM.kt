package com.example.ete.ui.main.profile.setting

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ete.data.Constant.PrefsKeys.DEVICE_ID
import com.example.ete.data.bean.ApiResponse
import com.example.ete.data.remote.ApiRepositoryImpl
import com.example.ete.data.remote.helper.Resource
import com.example.ete.util.AppUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingActivityVM @Inject constructor(private val apiRepoImpl: ApiRepositoryImpl) : ViewModel() {

    /** Logout **/
    val obrLogout = MutableLiveData<Resource<ApiResponse<Any>>>()
    fun callSocialLoginApi(context: Context) = viewModelScope.launch {
        val rqMap = HashMap<String, Any>()
        rqMap[DEVICE_ID] = AppUtils.getDeviceId(context)

        apiRepoImpl.logout(rqMap).collect {
            obrLogout.value = it
        }
    }

    /** Delete **/
    val obrDeleteAccount = MutableLiveData<Resource<ApiResponse<Any>>>()
    fun callDeleteAccountApi() = viewModelScope.launch {
        apiRepoImpl.deleteAccount().collect {
            obrDeleteAccount.value = it
        }
    }
}