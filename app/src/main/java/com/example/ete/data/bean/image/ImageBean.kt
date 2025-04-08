package com.example.ete.data.bean.image

import com.google.gson.annotations.SerializedName

data class ImageBean(
    @SerializedName("id")
    val id: Long = 0L,

    @SerializedName("imageUrl")
    var imageUrl: String? = null,

    @SerializedName("createdAt")
    val createdAt: Long = 0L,

    val isUploading: Boolean = false,

    val isVideo: Boolean = false
)