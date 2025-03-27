package com.example.ete.data.bean.user

import com.example.ete.data.bean.Authentication
import com.example.ete.data.bean.country.CountryBean
import com.example.ete.util.AppUtils
import com.google.gson.annotations.SerializedName
import java.io.Serializable

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
    var email: String? = null,

    @SerializedName("countryCode")
    var countryCode: String? = null,

    @SerializedName("phone")
    var phone: String? = null,

    @SerializedName("profilePic")
    var profilePic: String? = null,

    @SerializedName("providerType")
    var providerType: String? = null,

    @SerializedName("isSocialLoggedIn")
    val isSocialLoggedIn: Boolean = false,

    @SerializedName("isFirstTime")
    val isFirstTime: Boolean = false,

    @SerializedName("dateOfBirth")
    var dateOfBirth: Long? = null,

    @SerializedName("gender")
    var gender: String? = null,

    @SerializedName("link")
    var link: String? = null,

    @SerializedName("bio")
    var bio: String? = null,

    @SerializedName("isBlocked")
    var isBlocked: Boolean = false,

    @SerializedName("country")
    var country: CountryBean = CountryBean(),

    @SerializedName("ranking")
    var ranking: Int = 0,

    @SerializedName("postCount")
    var postCount: Long = 0,

    @SerializedName("followingCount")
    var followingCount: Long = 0,

    @SerializedName("followerCount")
    var followerCount: Long = 0,

    @SerializedName("createdAt")
    var createdAt: Long = 0L,

    @SerializedName("isFollowing")
    var isFollowing: Boolean = false,

    @SerializedName("isAccountRestricted")
    var isAccountRestricted: Boolean = false,

    @SerializedName("isAccountModerated")
    var isAccountModerated: Boolean = false,

    @SerializedName("isAccountWarned")
    var isAccountWarned: Boolean = false,

    @SerializedName("isBlockByMe")
    var isBlockByMe: Boolean = false,

    @SerializedName("isBlockByUser")
    var isBlockByUser: Boolean = false,

    @SerializedName("authentication")
    val authentication: Authentication = Authentication(),

    var isProgress: Boolean = false
) : Serializable {

    fun getUsernameString(): String {
        return userName?.trim()?.takeIf { it.isNotEmpty() }?.let { "@$it" }.orEmpty()
    }

    fun getPostCountString(): String {
        return AppUtils.getCounterString(postCount)
    }

    fun getFollowingCountString(): String {
        return AppUtils.getCounterString(followingCount)
    }

    fun getFollowerCountString(): String {
        return AppUtils.getCounterString(followerCount)
    }
}