package com.example.ete.data.interceptor

import com.example.ete.di.MyApplication
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Provider

class AuthorizationInterceptor @Inject constructor(
    private val okHttpClient: Provider<OkHttpClient>
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val mainResponse = chain.proceed(chain.request())
        if (mainResponse.code == 401 || mainResponse.code == 403) {
            handleResponseCode(mainResponse.code)
        }
        return mainResponse
    }

    private fun handleResponseCode(code: Int) {
        try {
            okHttpClient.get().dispatcher.cancelAll()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        when (code) {
            401 -> MyApplication.instance?.restartApp()
        }
    }
}
