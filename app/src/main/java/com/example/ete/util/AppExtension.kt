package com.example.ete.util

import android.content.Context

//Get App Version
fun Context.getAppVersionCode(): Int {
    return packageManager.getPackageInfo(packageName, 0).longVersionCode.toInt()
}