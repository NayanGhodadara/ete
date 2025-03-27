package com.example.ete.data.bean.update

import com.google.gson.annotations.SerializedName

data class UpdateBean(
    @SerializedName("status")
    val status: Int = 0,

    @SerializedName("link")
    val link: String = ""
)
