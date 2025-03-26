package com.example.ete.data.remote.helper

import android.content.Context
import com.example.ete.R
import com.example.ete.di.MyApplication.Companion.instance
import org.json.JSONObject
import java.io.IOException
import java.net.HttpURLConnection

class NetworkErrorHandler(private val context: Context) {

    fun getErrMsg(throwable: Throwable): String {
        var errMsg = context.getString(R.string.error_found)
        throwable.printStackTrace()
        if (throwable is retrofit2.HttpException) {
            errMsg = getErrorMessage(throwable)
            if (throwable.code() == HttpURLConnection.HTTP_OK) instance?.restartApp()
        } else if (throwable is IOException) {
            errMsg = context.getString(R.string.network_error)
        } else {
            if (throwable.message != null) errMsg = throwable.message!!
        }
        return errMsg
    }

    private fun getErrorMessage(throwable: Throwable): String {
        return try {
            val httpException = throwable as retrofit2.HttpException
            val errorBody = httpException.response()!!.errorBody()
            var errMsg = context.getString(R.string.error_found)
            if (errorBody != null) {
                val jsonObject = JSONObject(errorBody.string())
                errMsg = jsonObject.getString("error")
            }
            errMsg
        } catch (e: Exception) {
            (throwable as retrofit2.HttpException).message()
        }
    }
}