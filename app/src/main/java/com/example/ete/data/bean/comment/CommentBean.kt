package com.example.ete.data.bean.comment

import android.content.Context
import com.example.ete.data.bean.user.UserBean
import com.example.ete.util.date_time.DateTimeStyle
import com.example.ete.util.date_time.DateTimeUnits
import com.example.ete.util.date_time.DateTimeUtils
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class CommentBean(
    @SerializedName("id")
    val id: Long? = null,

    @SerializedName("comment")
    val comment: String? = null,

    @SerializedName("user")
    var user: UserBean? = UserBean(),

    @SerializedName("postCommentReply")
    var postCommentReply: ArrayList<CommentBean> = ArrayList(),

    @SerializedName("postComment")
    var postComment: CommentedUserBean? = CommentedUserBean(),

    @SerializedName("createdAt")
    val createdAt: Long = 0L,

    var isLimited: Boolean = true

) : Serializable {

    fun getCreatedTimeText(context: Context): String {
        return try {
            DateTimeUtils.getTimeAgo(context, DateTimeUtils.formatDate(createdAt, DateTimeUnits.SECONDS), DateTimeStyle.AGO_SHORT_STRING)
        } catch (e: Exception) {
            e.printStackTrace()
            "few hours ago"
        }
    }
}