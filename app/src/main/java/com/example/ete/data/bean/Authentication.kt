package com.example.ete.data.bean

import com.google.gson.annotations.SerializedName

data class Authentication(
    @SerializedName("accessToken")
    val accessToken: String = "",

    @SerializedName("refreshToken")
    val refreshToken: String = "",

    @SerializedName("expiresAt")
    val expiresAt: Long = 0,

    var jwtToken: String = "",
)