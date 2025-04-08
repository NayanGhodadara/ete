package com.example.ete.data.bean.library

import android.graphics.drawable.Drawable

data class LibraryPostDetailBean(
    val icon: Drawable? = null,
    val title: String? = null,
    val value: String? = null,
    var information: String? = null
)