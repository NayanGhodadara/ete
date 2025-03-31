package com.example.ete.data.bean

import com.google.gson.annotations.SerializedName


open class ApiResponse<Any>(
    @SerializedName("data")
    var data: Any? = null,

    @SerializedName("daysToNextMaturity")
    var daysToNextMaturity: Long = 0L,

    @SerializedName("message")
    val message: String = "",

    @SerializedName("error")
    var error: String = "",

    @SerializedName("meta")
    val meta: Meta = Meta()
)

data class Meta(
    @SerializedName("totalItems")
    val totalItems: Int = 0,

    @SerializedName("itemsPerPage")
    val itemsPerPage: Int = 0,

    @SerializedName("totalPages")
    val totalPages: Int = 0,

    @SerializedName("currentPage")
    val currentPage: Int = 0,

    @SerializedName("isLastPage")
    val isLastPage: Boolean = false
)

