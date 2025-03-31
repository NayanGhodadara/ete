@file:Suppress("unused")

package com.example.ete.di.viewmodel

import android.view.HapticFeedbackConstants
import android.view.View
import androidx.lifecycle.ViewModel
import com.example.ete.di.MyApplication
import com.example.ete.di.event.SingleLiveEvent
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel : ViewModel() {

    @JvmField
    val compositeDisposable: CompositeDisposable = CompositeDisposable()

    @JvmField
    val obrClick: SingleLiveEvent<View> = SingleLiveEvent()

    fun onClick(view: View) {
        obrClick.value = view

        @Suppress("DEPRECATION")
        view.performHapticFeedback(
            HapticFeedbackConstants.VIRTUAL_KEY,
            // Ignore device's setting. Otherwise, you can use FLAG_IGNORE_VIEW_SETTING to ignore view's setting.
            HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING
        )
    }

    //Get network message
    fun getNetworkMsg(e: Exception): String {
        return MyApplication.instance?.networkErrorHandler?.getErrMsg(e) ?: ""
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}