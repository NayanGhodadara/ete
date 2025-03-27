package com.example.ete.data.bean.dropdown

import com.google.gson.annotations.SerializedName

data class DropDownListBean(
    @SerializedName("plantType")
    val plantType: ArrayList<DropDownBean> = ArrayList(),

    @SerializedName("reportReasons")
    val reportReasons: ArrayList<DropDownBean> = ArrayList(),

    @SerializedName("postType")
    val postType: ArrayList<DropDownBean> = ArrayList(),

    @SerializedName("sowingType")
    val sowingType: ArrayList<DropDownBean> = ArrayList(),

    @SerializedName("sunExposure")
    val sunExposure: ArrayList<DropDownBean> = ArrayList(),
)