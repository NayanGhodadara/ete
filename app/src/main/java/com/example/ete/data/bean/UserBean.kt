package com.example.ete.data.bean

import com.google.gson.annotations.SerializedName

data class UserBean(
    @SerializedName("id")
    var id: Long? = null,

    @SerializedName("userUniqueId")
    var userUniqueId: String? = null,

    @SerializedName("name")
    var name: String? = null,

    @SerializedName("userName")
    var userName: String? = null,

    @SerializedName("email")
    var email: String? = null
)