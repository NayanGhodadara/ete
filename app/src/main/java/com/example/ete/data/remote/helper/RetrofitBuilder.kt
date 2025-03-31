package com.example.ete.data.remote.helper

import android.util.Log
import com.example.ete.BuildConfig
import com.example.ete.data.EndPoint.URLs.BASE_URL
import com.example.ete.data.interceptor.AuthorizationInterceptor
import com.example.ete.data.interceptor.RequestInterceptor
import com.example.ete.data.remote.ApiInterface
import com.example.ete.data.remote.ApiRepositoryImpl
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RetrofitBuilder {

    private val httpLoggingInterceptor =
        HttpLoggingInterceptor { message -> Log.e("Retrofit", message) }
            .setLevel(
                if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
                else HttpLoggingInterceptor.Level.NONE
            )

    private fun getDispatcherForAPI(): Dispatcher {
        val dispatcher = Dispatcher()
        dispatcher.maxRequests = 1
        return dispatcher
    }

    @Provides
    @Singleton
    fun getOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(RequestInterceptor())
            .addInterceptor(AuthorizationInterceptor())
            .connectTimeout(100, TimeUnit.SECONDS)
            .readTimeout(100, TimeUnit.SECONDS)
            .writeTimeout(100, TimeUnit.SECONDS)
            .dispatcher(getDispatcherForAPI())
            .build()
    }

    @Provides
    @Singleton
    fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(getOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
    }

    @Provides
    @Singleton
    fun apiInterfaceImpl(): ApiRepositoryImpl {
        val apiInterface: ApiInterface = getRetrofit().create(ApiInterface::class.java)
        return ApiRepositoryImpl(apiInterface)
    }
}