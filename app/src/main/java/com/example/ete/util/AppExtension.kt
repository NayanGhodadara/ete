package com.example.ete.util

import android.app.Activity
import android.content.Context
import androidx.fragment.app.Fragment
import com.example.ete.BuildConfig
import timber.log.Timber

//Get App Version
fun Context.getAppVersionCode(): Int {
    return packageManager.getPackageInfo(packageName, 0).longVersionCode.toInt()
}


//Log for activity
@Suppress("unused")
fun Activity.loggerD(msg: String) {
    if (BuildConfig.DEBUG)
        Timber.d(msg)
}

@Suppress("unused")
fun Activity.loggerE(msg: String) {
    if (BuildConfig.DEBUG)
        Timber.e(this.localClassName, msg)
}

@Suppress("unused")
fun Activity.loggerI(msg: String) {
    if (BuildConfig.DEBUG)
        Timber.i(this.localClassName, msg)
}

@Suppress("unused")
fun Activity.loggerV(msg: String) {
    if (BuildConfig.DEBUG)
        Timber.v(this.localClassName, msg)
}

@Suppress("unused")
fun Activity.loggerW(msg: String) {
    if (BuildConfig.DEBUG)
        Timber.w(this.localClassName, msg)
}

//Log for fragment
@Suppress("unused")
fun Fragment.loggerD(msg: String) {
    if (BuildConfig.DEBUG)
        Timber.d(this.javaClass.simpleName, msg)
}

@Suppress("unused")
fun Fragment.loggerE(msg: String) {
    if (BuildConfig.DEBUG)
        Timber.e(this.javaClass.simpleName, msg)
}

@Suppress("unused")
fun Fragment.loggerI(msg: String) {
    if (BuildConfig.DEBUG)
        Timber.i(this.javaClass.simpleName, msg)
}

@Suppress("unused")
fun Fragment.loggerV(msg: String) {
    if (BuildConfig.DEBUG)
        Timber.v(this.javaClass.simpleName, msg)
}

@Suppress("unused")
fun Fragment.loggerW(msg: String) {
    if (BuildConfig.DEBUG)
        Timber.w(this.javaClass.simpleName, msg)
}

//Log for context
@Suppress("unused")
fun Context.loggerD(msg: String) {
    if (BuildConfig.DEBUG)
        Timber.d(this.javaClass.simpleName, msg)
}

@Suppress("unused")
fun Context.loggerE(msg: String) {
    if (BuildConfig.DEBUG)
        Timber.e(this.javaClass.simpleName, msg)
}

@Suppress("unused")
fun Context.loggerI(msg: String) {
    if (BuildConfig.DEBUG)
        Timber.i(this.javaClass.simpleName, msg)
}

@Suppress("unused")
fun Context.loggerV(msg: String) {
    if (BuildConfig.DEBUG)
        Timber.v(this.javaClass.simpleName, msg)
}

@Suppress("unused")
fun Context.loggerW(msg: String) {
    if (BuildConfig.DEBUG)
        Timber.w(this.javaClass.simpleName, msg)
}
