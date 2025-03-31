package com.example.ete.data.bean.aws

import com.google.gson.annotations.SerializedName

data class AWSBean(
    @SerializedName("IdentityId")
    val identityId: String = "",

    @SerializedName("Token")
    val token: String = ""
)