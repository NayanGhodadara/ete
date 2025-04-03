package com.example.ete.ui.welcome.nav

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.amazonaws.auth.CognitoCachingCredentialsProvider
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener
import com.amazonaws.mobileconnectors.s3.transferutility.TransferNetworkLossHandler
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility
import com.amazonaws.regions.Region
import com.amazonaws.regions.Regions
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3Client
import com.example.ete.data.Constant.AWSObject.AWS_IDENTITY_POOL_ID
import com.example.ete.data.Constant.AWSObject.AWS_REGION
import com.example.ete.data.Constant.AWSObject.PROFILE_IMAGE_PATH
import com.example.ete.data.Constant.AWSObject.SOMETHING_WENT_WRONG
import com.example.ete.data.Constant.AWSObject.STORAGE_NAME
import com.example.ete.data.Constant.ApiObject.BIO
import com.example.ete.data.Constant.ApiObject.COUNTRY_CODE
import com.example.ete.data.Constant.ApiObject.COUNTRY_ID
import com.example.ete.data.Constant.ApiObject.DOB
import com.example.ete.data.Constant.ApiObject.EMAIL
import com.example.ete.data.Constant.ApiObject.GENDER
import com.example.ete.data.Constant.ApiObject.LINK
import com.example.ete.data.Constant.ApiObject.NAME
import com.example.ete.data.Constant.ApiObject.ORDER_ID
import com.example.ete.data.Constant.ApiObject.OTP
import com.example.ete.data.Constant.ApiObject.PHONE
import com.example.ete.data.Constant.ApiObject.PROFILE_PIC
import com.example.ete.data.Constant.ApiObject.USER_NAME
import com.example.ete.data.bean.ApiResponse
import com.example.ete.data.bean.auth.AuthBean
import com.example.ete.data.bean.otp.OtpBean
import com.example.ete.data.bean.user.UserBean
import com.example.ete.data.remote.ApiRepositoryImpl
import com.example.ete.data.remote.helper.Resource
import com.example.ete.di.MyApplication
import com.example.ete.di.event.SingleLiveEvent
import com.example.ete.util.AppUtils
import com.example.ete.util.aws.DeveloperAuthenticationProvider
import com.example.ete.util.loggerE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class AuthActivityVM @Inject constructor(private val apiRepoImpl: ApiRepositoryImpl) : ViewModel() {

    val isLoading = mutableStateOf(false)
    var authBean = AuthBean()
    var userBean = UserBean()

    /** Social login **/
    private val _obrSocialLogin = MutableLiveData<Resource<ApiResponse<UserBean>>>()
    val obrSocialLogin: LiveData<Resource<ApiResponse<UserBean>>> get() = _obrSocialLogin
    fun callSocialLoginApi() = viewModelScope.launch {
        val rqMap = HashMap<String, Any>()
        if (authBean.email.isEmpty().not()) {
            rqMap[EMAIL] = authBean.email
        } else {
            rqMap[COUNTRY_CODE] = authBean.countryCode
            rqMap[PHONE] = authBean.phone
            rqMap[ORDER_ID] = authBean.orderId
        }
        rqMap[OTP] = authBean.otp

        apiRepoImpl.socialLogin(rqMap).collect {
            _obrSocialLogin.value = it
        }
    }

    /**
     * Send Otp
     * **/
    private val _obrSendOtp = MutableLiveData<Resource<ApiResponse<OtpBean>>>()
    val obrSendOtp: LiveData<Resource<ApiResponse<OtpBean>>> get() = _obrSendOtp
    fun callSendOtpAsync() = viewModelScope.launch {
        _obrSendOtp.value = Resource.loading(null)
        val rqMap = HashMap<String, Any>()
        if (authBean.email.isEmpty().not()) {
            rqMap[EMAIL] = authBean.email
        } else {
            rqMap[COUNTRY_CODE] = authBean.countryCode
            rqMap[PHONE] = authBean.phone
        }

        apiRepoImpl.sendOtpAsync(rqMap).collect {
            _obrSendOtp.postValue(it)
        }
    }

    /**
     *  Verify
     * **/
    private val _obrVerify = MutableLiveData<Resource<ApiResponse<UserBean>>>()
    val obrVerify: LiveData<Resource<ApiResponse<UserBean>>> get() = _obrVerify

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

        apiRepoImpl.verifyOtpAsync(rqMap).collect {
            _obrVerify.postValue(it)
        }
    }

    /**
     *  Create account
     * **/
    private val _obrCreateAccount = MutableLiveData<Resource<ApiResponse<UserBean>>>()
    val obrCreateAccount: LiveData<Resource<ApiResponse<UserBean>>> get() = _obrCreateAccount

    fun callCreateAccountApi() = viewModelScope.launch {
        val rqMap = HashMap<String, Any>()
        rqMap[USER_NAME] = userBean.userName.orEmpty().trim()
        rqMap[NAME] = userBean.name.orEmpty().trim()
        rqMap[BIO] = userBean.bio.orEmpty().trim()
        rqMap[GENDER] = userBean.gender.orEmpty().lowercase()
        if (userBean.dateOfBirth != null && userBean.dateOfBirth != 0L)
            rqMap[DOB] = userBean.dateOfBirth ?: 0L

        rqMap[LINK] = userBean.link.orEmpty().trim()
        rqMap[COUNTRY_ID] = userBean.country.id ?: 0L
        if (userBean.profilePic != null)
            rqMap[PROFILE_PIC] = userBean.profilePic.orEmpty()

        apiRepoImpl.updateProfile(rqMap).collect {
            _obrCreateAccount.postValue(it)
        }
    }

    //AWS file upload
    private val _obrUpload: MutableLiveData<Resource<String>> = SingleLiveEvent()
    val obrUpload: LiveData<Resource<String>> get() = _obrUpload
    fun uploadProfileImage(context: Context) {
        _obrUpload.postValue(Resource.loading(null))

        AppUtils.callAWSTokenAPI(apiRepoImpl = apiRepoImpl, callback = {
            if (it) {
                try {
                    val path = File(userBean.profilePic.orEmpty())

                    TransferNetworkLossHandler.getInstance(context)
                    val developerProvider = DeveloperAuthenticationProvider(apiRepoImpl, null, AWS_IDENTITY_POOL_ID, Regions.US_EAST_1)
                    val credentialsProvider = CognitoCachingCredentialsProvider(context, developerProvider, Regions.US_EAST_1)
                    val s3Client: AmazonS3 = AmazonS3Client(credentialsProvider, Region.getRegion(AWS_REGION))
                    val transferUtility = TransferUtility.builder().s3Client(s3Client).context(context).build()

                    val observer = transferUtility.upload(
                        STORAGE_NAME,
                        PROFILE_IMAGE_PATH + path.name,
                        path
                    )

                    observer.setTransferListener(object : TransferListener {
                        override fun onStateChanged(id: Int, state: TransferState) {
                            when (state) {
                                //Success
                                TransferState.COMPLETED -> {
                                    MyApplication.instance?.loggerE("::::: SUCCESS")
                                    val url = "https://$STORAGE_NAME.s3.$AWS_REGION.amazonaws.com/$PROFILE_IMAGE_PATH${path.name}"
                                    _obrUpload.postValue(Resource.success(url))
                                }

                                //Failed
                                TransferState.FAILED -> {
                                    _obrUpload.postValue(Resource.warn(null, SOMETHING_WENT_WRONG))
                                    MyApplication.instance?.loggerE("::::: FAIL")
                                }

                                //Other
                                TransferState.WAITING -> {}
                                TransferState.IN_PROGRESS -> {}
                                TransferState.PAUSED -> {}
                                TransferState.RESUMED_WAITING -> {}
                                TransferState.CANCELED -> {}
                                TransferState.WAITING_FOR_NETWORK -> {}
                                TransferState.PART_COMPLETED -> {}
                                TransferState.PENDING_CANCEL -> {}
                                TransferState.PENDING_PAUSE -> {}
                                TransferState.PENDING_NETWORK_DISCONNECT -> {}
                                TransferState.UNKNOWN -> {}
                            }
                        }

                        override fun onProgressChanged(id: Int, bytesCurrent: Long, bytesTotal: Long) {
                            Log.e("AWS", "onProgressChanged === $bytesTotal == $bytesCurrent")
                        }

                        override fun onError(id: Int, ex: Exception) {
                            _obrUpload.postValue(Resource.warn(null, ex.message.toString()))
                            MyApplication.instance?.loggerE("::::: ERROR : ${ex.message}")
                        }
                    })
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            } else {
                _obrUpload.postValue(Resource.warn(null, SOMETHING_WENT_WRONG))
            }
        })
    }
}