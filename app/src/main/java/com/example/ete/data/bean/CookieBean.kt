package com.example.ete.data.bean

import com.example.ete.util.cookie.CookieBarType

data class CookieBean(
    var message: String = "",
    val type: CookieBarType = CookieBarType.WARNING
)