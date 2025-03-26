package com.example.ete.data.remote

import com.example.ete.data.bean.ApiResponse
import com.example.ete.data.bean.UserBean
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface ApiInterface{

    @GET("V1/country-list")
    fun getCountryListAsync(): Deferred<Response<ApiResponse<ArrayList<Any>>>>
}