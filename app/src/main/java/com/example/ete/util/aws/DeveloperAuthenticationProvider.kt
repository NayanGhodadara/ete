package com.example.ete.util.aws

import android.util.Log
import com.amazonaws.auth.AWSAbstractCognitoDeveloperIdentityProvider
import com.amazonaws.regions.Regions
import com.example.ete.data.Constant.AWSObject.AWS_IDENTITY_DEVELOPER_PROVIDER_NAME
import com.example.ete.data.Constant.PrefsKeys.AWS_TOKEN
import com.example.ete.data.Constant.PrefsKeys.IDENTITY_ID
import com.example.ete.data.bean.ApiResponse
import com.example.ete.data.bean.aws.AWSBean
import com.example.ete.data.remote.ApiRepositoryImpl
import com.example.ete.data.remote.helper.ApiCallback
import com.example.ete.di.MyApplication
import com.example.ete.util.prefs.Prefs
import retrofit2.Response

class DeveloperAuthenticationProvider(private val apiRepoImpl: ApiRepositoryImpl, accountId: String?, identityPoolId: String, region: Regions) : AWSAbstractCognitoDeveloperIdentityProvider(accountId, identityPoolId, region) {

    var awsBean = AWSBean()

    override fun getProviderName(): String {
        return AWS_IDENTITY_DEVELOPER_PROVIDER_NAME
    }

    override fun refresh(): String {
        setToken(null)

        Log.e("Test", ":::: AWS Refresh")

        if (MyApplication.instance?.isApiCallRunning == false) {
            Log.e("test", "refresh and call aws")
            callAWSTokenAPI(apiRepoImpl)
        }

        val poolId = Prefs.getString(IDENTITY_ID, "")
        val awsToken = Prefs.getString(AWS_TOKEN, "")

        update(poolId, awsToken)
        return awsToken.orEmpty()
    }

    override fun getIdentityId(): String {
        identityId = Prefs.getString(IDENTITY_ID, "")
        return if (identityId == null || identityId == "") {
            Prefs.getString(IDENTITY_ID, "").orEmpty()
        } else {
            identityId
        }
    }

    //Call AWS token API
    private fun callAWSTokenAPI(apiRepoImpl: ApiRepositoryImpl) {
        MyApplication.instance?.isApiCallRunning = true

        apiRepoImpl.awsToken(object : ApiCallback<Response<ApiResponse<AWSBean>>>() {
            override fun onLoading() {
            }

            override fun onSuccess(response: Response<ApiResponse<AWSBean>>) {
                Prefs.putString(IDENTITY_ID, response.body()?.data?.identityId)
                Prefs.putString(AWS_TOKEN, response.body()?.data?.token)
                awsBean = response.body()?.data ?: AWSBean()
                MyApplication.instance?.isApiCallRunning = false
            }

            override fun onFailed(message: String) {
                MyApplication.instance?.isApiCallRunning = false
                Log.e("test", "ERROR While call aws : $message")
            }

            override fun onErrorThrow(exception: Exception) {
                Log.e("test", "ERROR While call aws : ${exception.message}")
                MyApplication.instance?.isApiCallRunning = false
            }
        })
    }
}