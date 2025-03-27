package com.example.ete.data.remote

import com.example.ete.data.bean.dropdown.DropDownListBean
import com.example.ete.data.EndPoint
import com.example.ete.data.bean.ApiResponse
import com.example.ete.data.bean.country.CountryBean
import com.example.ete.data.bean.update.UpdateBean
import com.example.ete.data.bean.user.UserBean
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiInterface {

    /** User **/
    @GET(EndPoint.User.USERS)
    fun getUserAsync(): Deferred<Response<ApiResponse<UserBean>>>

    @GET(EndPoint.DropDown.COUNTRY_LIST)
    fun getCountryListAsync(): Deferred<Response<ApiResponse<ArrayList<CountryBean>>>>

    /** DropDownList **/
    @GET(EndPoint.DropDown.DROP_DOWN_LIST)
    fun getDropDownListAsync(): Deferred<Response<ApiResponse<DropDownListBean>>>


    /** App Version **/
    @POST(EndPoint.AppVersion.CHECK_APP_VERSION)
    fun checkAppVersionAsync(@Body data: HashMap<String, @JvmSuppressWildcards Any>): Deferred<Response<ApiResponse<UpdateBean>>>
}