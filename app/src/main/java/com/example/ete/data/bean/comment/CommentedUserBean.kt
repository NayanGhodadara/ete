package com.example.ete.data.bean.comment

import com.example.ete.data.bean.user.UserBean
import com.google.gson.annotations.SerializedName

data class CommentedUserBean(

    @SerializedName("id")
    var id: Long = 0L,

    @SerializedName("comment")
    var comment: String? = null,

    @SerializedName("user")
    var user: UserBean = UserBean(),

    @SerializedName("createdAt")
    var createdAt: Long = 0L,
)