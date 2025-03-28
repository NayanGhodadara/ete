package com.example.ete.data.remote

import com.example.ete.data.bean.ApiResponse
import com.example.ete.data.bean.country.CountryBean
import com.example.ete.data.bean.dropdown.DropDownListBean
import com.example.ete.data.bean.otp.OtpBean
import com.example.ete.data.bean.update.UpdateBean
import com.example.ete.data.bean.user.UserBean
import com.example.ete.data.remote.helper.ApiUtils.getAPIError
import com.example.ete.data.remote.helper.ApiUtils.getNetworkErrorMessage
import com.example.ete.data.remote.helper.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiRepositoryImpl
@Inject constructor(
    private val apiInterface: ApiInterface
) {

    /** Auth **/
    fun sendOtpAsync(data: HashMap<String, Any>): Flow<Resource<ApiResponse<OtpBean>>> = flow {
        emit(Resource.loading(null))
        try {
            val response = apiInterface.sendOtpAsync(data).await()
            if (response.isSuccessful) {
                emit(Resource.success(response.body() ?: return@flow))
            } else {
                val errorMsg = getAPIError(response.errorBody() ?: return@flow)
                emit(Resource.warn(null, errorMsg))
            }
        } catch (e: Exception) {
            emit(Resource.error(null, getNetworkErrorMessage(e)))
        }
    }

    fun verifyOtpAsync(data: HashMap<String, Any>): Flow<Resource<ApiResponse<UserBean>>> = flow {
        emit(Resource.loading(null))
        try {
            val response = apiInterface.verifyOtpAsync(data).await()
            if (response.isSuccessful) {
                emit(Resource.success(response.body() ?: return@flow))
            } else {
                val errorMsg = getAPIError(response.errorBody() ?: return@flow)
                emit(Resource.warn(null, errorMsg))
            }
        } catch (e: Exception) {
            emit(Resource.error(null, getNetworkErrorMessage(e)))
        }
    }


    /** User **/
    fun getUserAsync(): Flow<Resource<ApiResponse<UserBean>>> = flow {
        emit(Resource.loading(null))
        try {
            val response = apiInterface.getUserAsync().await()
            if (response.isSuccessful) {
                emit(Resource.success(response.body() ?: return@flow))
            } else {
                val errorMsg = getAPIError(response.errorBody() ?: return@flow)
                emit(Resource.warn(null, errorMsg))
            }
        } catch (e: Exception) {
            emit(Resource.error(null, getNetworkErrorMessage(e)))
        }
    }

    fun getCountryList(): Flow<Resource<ApiResponse<ArrayList<CountryBean>>>> = flow {
        emit(Resource.loading(null))
        try {
            val response = apiInterface.getCountryListAsync().await()
            if (response.isSuccessful) {
                emit(Resource.success(response.body() ?: return@flow))
            } else {
                val errorMsg = getAPIError(response.errorBody() ?: return@flow)
                emit(Resource.warn(null, errorMsg))
            }
        } catch (e: Exception) {
            emit(Resource.error(null, getNetworkErrorMessage(e)))
        }
    }

    /** Drop down **/
    fun getDropDownList(): Flow<Resource<ApiResponse<DropDownListBean>>> = flow {
        emit(Resource.loading(null))
        try {
            val response = apiInterface.getDropDownListAsync().await()
            if (response.isSuccessful) {
                emit(Resource.success(response.body() ?: return@flow))
            } else {
                val errorMsg = getAPIError(response.errorBody() ?: return@flow)
                emit(Resource.warn(null, errorMsg))
            }
        } catch (e: Exception) {
            emit(Resource.error(null, getNetworkErrorMessage(e)))
        }
    }

    /** App version **/
    fun checkAppVersion(data: HashMap<String, Any>): Flow<Resource<ApiResponse<UpdateBean>>> = flow {
        emit(Resource.loading(null))
        try {
            val response = apiInterface.checkAppVersionAsync(data).await()
            if (response.isSuccessful) {
                emit(Resource.success(response.body() ?: return@flow))
            } else {
                val errorMsg = getAPIError(response.errorBody() ?: return@flow)
                emit(Resource.warn(null, errorMsg))
            }
        } catch (e: Exception) {
            emit(Resource.error(null, getNetworkErrorMessage(e)))
        }
    }
}