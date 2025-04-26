package com.example.ete.data.bean.post

import android.annotation.SuppressLint
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import kotlin.random.Random

data class PostMediaBean(
    @SerializedName("isImage")
    val isImage: Boolean = true,

    @SerializedName("fileUrl")
    val fileUrl: String? = null,

    @SerializedName("thumbnailUrl")
    val thumbnailUrl: String? = null,

    @SerializedName("id")
    val id: Int = Random.Default.nextInt(1000000),

    var isUploading: Boolean = true,
    var compressMediaPath: String? = null,
    var awsUrl: String? = null,
    var videoThumbnail: String? = null,
    var isVideoPlaying: Boolean = false,
    val isAudio: Boolean = false,
    val isPost: Boolean = true,
    val isForUpload: Boolean = false,
    var progress: Float = 0f,
) : Serializable {

    @SuppressLint("DefaultLocale")
    fun getProgressString(): String {
        return String.format("%.2f", progress) + "%"
    }
}