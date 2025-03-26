package com.example.ete.util

import com.example.ete.data.bean.ApiResponse
import com.google.gson.Gson
import okhttp3.ResponseBody

object AppUtil {

    //Convert api error body
    fun getAPIError(errorBody: ResponseBody?): String {
        return try {
            val apiRes: ApiResponse<Any> = ApiResponse()
            val apiResponse: ApiResponse<Any>? =
                Gson().fromJson(errorBody?.string(), apiRes::class.java)
            apiResponse?.message.orEmpty()
        } catch (e: Exception) {
            errorBody?.string().orEmpty()
        }
    }
}