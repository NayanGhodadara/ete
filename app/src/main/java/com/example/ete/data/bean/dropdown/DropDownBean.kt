package com.example.ete.data.bean.dropdown

import com.google.gson.annotations.SerializedName

data class DropDownBean(
    @SerializedName("id")
    val id: Long = 0L,

    @SerializedName("title")
    var title: String = "",

    @SerializedName("createdAt")
    val createdAt: Long = 0L,

    var isSelected: Boolean = false,

    var otherOption: String? = null,

    @SerializedName("label")
    val label: String = "",

    val isToggle: Boolean = false,
    var isToggleSelected: Boolean = false,
)