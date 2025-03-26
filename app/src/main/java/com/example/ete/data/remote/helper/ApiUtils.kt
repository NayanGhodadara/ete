package com.example.ete.data.remote.helper

import com.example.ete.R
import com.example.ete.data.bean.ApiResponse
import com.example.ete.di.MyApplication.Companion.instance
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.HttpException
import java.io.File
import java.io.IOException
import java.net.HttpURLConnection
import java.net.SocketTimeoutException

object ApiUtils {

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

    private fun getErrorMessage(throwable: Throwable): String {
        return try {
            val httpException = throwable as HttpException
            val errorBody = httpException.response()!!.errorBody()
            var errMsg = instance?.getString(R.string.error_found).orEmpty()
            if (errorBody != null) {
                val jsonObject = JSONObject(errorBody.string())
                errMsg = jsonObject.getString("error")
            }
            errMsg
        } catch (e: Exception) {
            (throwable as HttpException).message()
        }
    }

    fun getNetworkErrorMessage(throwable: Throwable): String {
        var errMsg = instance?.getString(R.string.error_found)
        throwable.printStackTrace()
        if (throwable is HttpException) {
            errMsg = getErrorMessage(throwable)
            if (throwable.code() == HttpURLConnection.HTTP_OK) instance?.restartApp()
        } else if (throwable is SocketTimeoutException) {
            errMsg = instance?.getString(R.string.timeout)
        } else if (throwable is IOException) {
            errMsg = instance?.getString(R.string.network_error)
        } else {
            if (throwable.message != null) errMsg = throwable.message!!
        }
        return errMsg.orEmpty()
    }

    //Convert files to multipart
    fun createMultipartBodyForImage(file: File?, keyName: String): MultipartBody.Part? {
        return if (file != null) {
            MultipartBody.Part.createFormData(keyName, file.name, file.asRequestBody("image/jpg".toMediaTypeOrNull()))
        } else {
            null
        }
    }

    fun createMultipartBodyForVideo(file: File?, keyName: String): MultipartBody.Part {
        return if (file != null) {
            MultipartBody.Part.createFormData(keyName, file.name, file.asRequestBody("video/*".toMediaTypeOrNull()))
        } else {
            MultipartBody.Part.createFormData(keyName, "", "".toRequestBody("text/plain".toMediaTypeOrNull()))
        }
    }

    fun createMultipartBodyForFile(file: File?, keyName: String): MultipartBody.Part {
        return if (file != null) {
            MultipartBody.Part.createFormData(keyName, file.name, file.asRequestBody("*/*".toMediaTypeOrNull()))
        } else {
            MultipartBody.Part.createFormData(keyName, "", "".toRequestBody("text/plain".toMediaTypeOrNull()))
        }
    }

    fun createMultipartBodyForDocument(file: File?, keyName: String): MultipartBody.Part? {
        return if (file != null) {
            MultipartBody.Part.createFormData(keyName, file.name, file.asRequestBody("*/*".toMediaTypeOrNull()))
        } else {
            null
        }
    }
}