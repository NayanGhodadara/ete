
package com.example.ete.di.dagger

import android.content.Context
import android.util.Log
import com.example.ete.data.interceptor.AuthorizationInterceptor
import com.example.ete.data.interceptor.RequestInterceptor
import com.example.ete.data.remote.ApiInterface
import com.example.ete.data.remote.ApiRepositoryImpl
import com.example.ete.data.remote.helper.NetworkErrorHandler
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Provider
import javax.inject.Singleton

@Module
object NetworkModule {

    @Singleton
    @Provides
    fun provideAuthorizationInterceptor(okHttpClientProvider: Provider<OkHttpClient>): AuthorizationInterceptor {
        return AuthorizationInterceptor(okHttpClientProvider)
    }

    @Singleton
    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor { message -> Log.e("Retrofit", message) }
            .setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Singleton
    @Provides
    fun provideDispatcher(): Dispatcher {
        return Dispatcher().apply { maxRequests = 1 }
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        dispatcher: Dispatcher,
        authorizationInterceptor: AuthorizationInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(authorizationInterceptor)
            .addInterceptor(RequestInterceptor())
            .connectTimeout(5, TimeUnit.MINUTES)
            .readTimeout(5, TimeUnit.MINUTES)
            .writeTimeout(5, TimeUnit.MINUTES)
            .dispatcher(dispatcher)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://stage-api.ete.space/api/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
    }

    @Singleton
    @Provides
    fun provideApiInterface(retrofit: Retrofit): ApiInterface {
        return retrofit.create(ApiInterface::class.java)
    }

    @Singleton
    @Provides
    fun provideApiRepository(apiInterface: ApiInterface): ApiRepositoryImpl {
        return ApiRepositoryImpl(apiInterface)
    }
}
