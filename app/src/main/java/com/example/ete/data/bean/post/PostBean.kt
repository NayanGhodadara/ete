package com.example.ete.data.bean.post

import android.content.Context
import com.example.ete.data.bean.post.PostMediaBean
import com.example.ete.data.Constant
import com.example.ete.data.bean.comment.CommentedUserBean
import com.example.ete.util.date_time.DateTimeUnits
import com.example.ete.util.date_time.DateTimeUtils
import com.example.ete.data.bean.dropdown.DropDownBean
import com.example.ete.data.bean.library.LibraryBean
import com.example.ete.data.bean.user.UserBean
import com.example.ete.util.AppUtils
import com.example.ete.util.prefs.Prefs
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class PostBean(
    @SerializedName("id")
    var id: Long = 0L,

    @SerializedName("caption")
    val caption: String? = null,

    @SerializedName("library")
    val library: LibraryBean? = null,

    @SerializedName("latitude")
    val latitude: Double? = null,

    @SerializedName("longitude")
    val longitude: Double? = null,

    @SerializedName("address")
    val address: String? = null,

    @SerializedName("postType")
    val postType: DropDownBean = DropDownBean(),

    @SerializedName("otherPostType")
    val otherPostType: String? = null,

    @SerializedName("user")
    var user: UserBean = UserBean(),

    @SerializedName("postImageVideo")
    var postImageVideo: ArrayList<PostMediaBean> = ArrayList(),

    @SerializedName("isPostLiked")
    var isPostLiked: Boolean = false,

    @SerializedName("isPostInWishlist")
    var isPostInWishlist: Boolean = false,

    @SerializedName("postCommentCount")
    var postCommentCount: Int = 0,

    @SerializedName("postLikeCount")
    var postLikeCount: Int = 0,

    @SerializedName("commentedUser")
    var commentedUser: CommentedUserBean? = null,

    @SerializedName("isPostRestricted")
    val isPostRestricted: Boolean = false,

    @SerializedName("isPostModerated")
    val isPostModerated: Boolean = false,

    @SerializedName("isPostWarned")
    val isPostWarned: Boolean = false,

    @SerializedName("createdAt")
    val createdAt: Long = 0L,

    @SerializedName("width")
    var width: Int = 1,

    @SerializedName("height")
    var height: Int = 1,

    var isMute: Boolean = Prefs.getBoolean(Constant.PrefsKeys.IS_MUTE, false),

    var isVideoClick: Boolean = false,

    var isJournal: Boolean = false,
    var showPopup: Boolean = false,
) : Serializable {

    fun getCreatedTimeText(context: Context): String {
        return try {
            DateTimeUtils.getTimeAgo(context, DateTimeUtils.formatDate(createdAt, DateTimeUnits.SECONDS))
        } catch (e: Exception) {
            e.printStackTrace()
            "few hours ago"
        }
    }

    fun getFileUrl(): String {
        return if (postImageVideo.isNotEmpty()) {
            postImageVideo[0].fileUrl.orEmpty()
        } else {
            ""
        }
    }

    fun isImage(): Boolean {
        return if (postImageVideo.isNotEmpty()) {
            postImageVideo[0].isImage
        } else {
            true
        }
    }

    fun getLikeCount(): String {
        return if (postLikeCount.toLong() == 0L) {
            ""
        } else {
            AppUtils.getCounterString(postLikeCount.toLong())
        }
    }

    fun getCommentCount(): String {
        return if (postCommentCount.toLong() == 0L) {
            ""
        } else {
            return AppUtils.getCounterString(postCommentCount.toLong())
        }
    }
}