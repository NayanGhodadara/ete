package com.example.ete.data.interceptor

import com.example.ete.data.EndPoint.Auth.SEND_OTP
import com.example.ete.di.MyApplication
import okhttp3.Interceptor
import okhttp3.Response

class AuthorizationInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val mainResponse = chain.proceed(chain.request())
        if (mainResponse.code == 401) {
            if (!chain.request().url.toString().contains(SEND_OTP)) {
                MyApplication.instance?.restartApp()
            }
        }
        return mainResponse
    }
}