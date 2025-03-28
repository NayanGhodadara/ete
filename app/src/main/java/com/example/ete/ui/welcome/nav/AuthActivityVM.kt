package com.example.ete.ui.welcome.nav

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.example.ete.data.Constant.ApiObject.COUNTRY_CODE
import com.example.ete.data.Constant.ApiObject.EMAIL
import com.example.ete.data.Constant.ApiObject.ORDER_ID
import com.example.ete.data.Constant.ApiObject.OTP
import com.example.ete.data.Constant.ApiObject.PHONE
import com.example.ete.data.bean.ApiResponse
import com.example.ete.data.bean.auth.AuthBean
import com.example.ete.data.bean.otp.OtpBean
import com.example.ete.data.bean.user.UserBean
import com.example.ete.data.remote.helper.Resource
import com.example.ete.di.event.SingleLiveEvent
import com.example.ete.di.viewmodel.BaseViewModel
import kotlinx.coroutines.launch

class AuthActivityVM : BaseViewModel() {

    val isLoading = mutableStateOf(false)
    var authBean = AuthBean()

    /**
     * Login
     * **/
    val obrSendOtp = SingleLiveEvent<Resource<ApiResponse<OtpBean>>>()

    fun callSendOtpAsync() = viewModelScope.launch {
        val rqMap = HashMap<String, Any>()
        if (authBean.email.isEmpty().not()) {
            rqMap[EMAIL] = authBean.email
        } else {
            rqMap[COUNTRY_CODE] = authBean.countryCode
            rqMap[PHONE] = authBean.phone
        }

        apiRepoImpl?.sendOtpAsync(rqMap)?.collect {
            obrSendOtp.value = it
        }
    }

    val obrVerify = SingleLiveEvent<Resource<ApiResponse<UserBean>>>()

    fun callVerifyOtp() = viewModelScope.launch {
        val rqMap = HashMap<String, Any>()
        if (authBean.email.isEmpty().not()) {
            rqMap[EMAIL] = authBean.email
        } else {
            rqMap[COUNTRY_CODE] = authBean.countryCode
            rqMap[PHONE] = authBean.phone
            rqMap[ORDER_ID] = authBean.orderId
        }
        rqMap[OTP] = authBean.otp

        apiRepoImpl?.verifyOtpAsync(rqMap)?.collect {
            obrVerify.value = it
        }
    }
}