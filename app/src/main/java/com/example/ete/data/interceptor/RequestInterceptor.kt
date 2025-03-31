@file:Suppress("UNUSED_VARIABLE")

package com.example.ete.data.interceptor

import android.util.Log
import com.example.ete.di.MyApplication
import com.example.ete.util.getAppVersionCode
import okhttp3.Interceptor
import okhttp3.Response
import java.nio.charset.Charset

class RequestInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val builder = request.newBuilder()
        builder.addHeader("Content-Type", "application/json")
        builder.addHeader("Accept", "application/json")

        try {
            builder.addHeader("Platform", "Android")
            builder.addHeader("Version", MyApplication.instance?.getAppVersionCode().toString())
        } catch (e: Exception) {
            e.printStackTrace()
        }


        try {
            builder.addHeader("Authorization", "Bearer ${MyApplication.instance?.getAuthData()?.accessToken.orEmpty()}")
            Log.w(this.javaClass.simpleName, "Authorization ${MyApplication.instance?.getAuthData()?.accessToken.orEmpty()}")
        } catch (e: Exception) {
            e.printStackTrace()
        }


        val response = chain.proceed(builder.build())

        //Use for response log
        val respString = getResponseString(response)
        //Log.i("respString", "intercept: $respString")

        return response
    }

    private fun getResponseString(response: Response): String {
        val responseBody = response.body
        val source = responseBody?.source()
        source?.request(java.lang.Long.MAX_VALUE) // Buffer the entire body.
        val buffer = source?.buffer
        var charset: Charset? = Charset.forName("UTF-8")
        val contentType = responseBody?.contentType()
        if (contentType != null) {
            charset = contentType.charset(Charset.forName("UTF-8"))
        }
        if (charset == null) {
            return ""
        }
        return buffer?.clone()?.readString(charset)!!
    }
}