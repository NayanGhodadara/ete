package com.example.ete.data.bean.country

import com.google.gson.annotations.SerializedName

data class CountryBean(
    val nameCode: String = "",

    val countryCode: String = "",

    @SerializedName("title")
    var title: String? = null,

    @SerializedName("id")
    val id: Long? = 0L,

    @SerializedName("createdAt")
    val createdAt: Long? = 0L,
) {
    fun getPlusCode(): String {
        return "+$countryCode"
    }
}