package com.example.ete.data.remote

import com.example.ete.data.bean.dropdown.DropDownListBean
import com.example.ete.data.EndPoint
import com.example.ete.data.bean.ApiResponse
import com.example.ete.data.bean.aws.AWSBean
import com.example.ete.data.bean.country.CountryBean
import com.example.ete.data.bean.otp.OtpBean
import com.example.ete.data.bean.update.UpdateBean
import com.example.ete.data.bean.user.UserBean
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface ApiInterface {

    /**  Auth  **/
    @POST(EndPoint.Auth.SEND_OTP)
    fun sendOtpAsync(@Body hashMap: HashMap<String, @JvmSuppressWildcards Any>): Deferred<Response<ApiResponse<OtpBean>>>

    @POST(EndPoint.Auth.VERIFY_OTP)
    fun verifyOtpAsync(@Body hashMap: HashMap<String, @JvmSuppressWildcards Any>): Deferred<Response<ApiResponse<UserBean>>>

    @POST(EndPoint.Auth.SOCIAL_LOGIN)
    fun socialLoginAsync(@Body data: HashMap<String, @JvmSuppressWildcards Any>): Deferred<Response<ApiResponse<UserBean>>>

    @POST(EndPoint.Auth.LOGOUT)
    fun logoutAsync(@Body hashMap: HashMap<String, @JvmSuppressWildcards Any>): Deferred<Response<ApiResponse<Any>>>


    /** User **/
    @GET(EndPoint.User.USERS)
    fun getUserAsync(): Deferred<Response<ApiResponse<UserBean>>>

    @PUT(EndPoint.User.USERS)
    fun updateProfileAsync(@Body data: HashMap<String, @JvmSuppressWildcards Any>): Deferred<Response<ApiResponse<UserBean>>>

    @GET(EndPoint.DropDown.COUNTRY_LIST)
    fun getCountryListAsync(): Deferred<Response<ApiResponse<ArrayList<CountryBean>>>>

    @DELETE(EndPoint.User.USERS)
    fun deleteAccountAsync(): Deferred<Response<ApiResponse<Any>>>

    /** DropDownList **/
    @GET(EndPoint.DropDown.DROP_DOWN_LIST)
    fun getDropDownListAsync(): Deferred<Response<ApiResponse<DropDownListBean>>>

    /** AWS **/
    @GET(EndPoint.AWS.AWS_TOKEN)
    fun awsTokenAsync(): Deferred<Response<ApiResponse<AWSBean>>>

    /** App Version **/
    @POST(EndPoint.AppVersion.CHECK_APP_VERSION)
    fun checkAppVersionAsync(@Body data: HashMap<String, @JvmSuppressWildcards Any>): Deferred<Response<ApiResponse<UpdateBean>>>
}