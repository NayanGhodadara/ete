package com.example.ete.data.bean.library

import android.content.Context
import com.example.ete.R
import com.example.ete.data.bean.dropdown.DropDownBean
import com.example.ete.data.bean.image.ImageBean
import com.example.ete.di.MyApplication
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class LibraryBean(
    @SerializedName("id")
    val id: Long = 0L,

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("water")
    val water: String? = null,

    @SerializedName("sun")
    val sun: DropDownBean? = null,

    @SerializedName("otherSun")
    val otherSun: String? = null,

    @SerializedName("care")
    val care: String? = null,

    @SerializedName("plantType")
    val plantType: DropDownBean? = DropDownBean(),

    @SerializedName("otherPlantType")
    val otherPlantType: String? = null,

    @SerializedName("stemLength")
    val stemLength: String? = null,

    @SerializedName("maturityTime")
    val maturityTime: String? = null,

    @SerializedName("plantingSeason")
    val plantingSeason: String? = null,

    @SerializedName("lifeCycle")
    val lifecycle: String? = null,

    @SerializedName("additionalInformation")
    val additionalInformation: String? = null,

    @SerializedName("temperature")
    val temperature: String? = null,

    @SerializedName("humidity")
    val humidity: String? = null,

    @SerializedName("sowingSeason")
    val sowingSeason: String? = null,

    @SerializedName("manure")
    val manure: String? = null,

    @SerializedName("plantUse")
    val plantUse: String? = null,

    @SerializedName("cropTime")
    val cropTime: String? = null,

    @SerializedName("isPinching")
    val isPinching: Boolean = false,

    @SerializedName("soilType")
    val soilType: String? = null,

    @SerializedName("plantSpacing")
    val plantSpacing: String? = null,

    @SerializedName("features")
    val features: String? = null,

    @SerializedName("funFact")
    val funFact: String? = null,

    @SerializedName("description")
    val description: String? = null,

    @SerializedName("fertilizer")
    val fertilizer: String? = null,

    @SerializedName("others")
    val others: String? = null,

    @SerializedName("siteSelection")
    val siteSelection: String? = null,

    @SerializedName("seedSowing")
    val seedSowing: String? = null,

    @SerializedName("germinationTime")
    val germinationTime: String? = null,

    @SerializedName("growingNotes")
    val growingNotes: String? = null,

    @SerializedName("images")
    val images: ArrayList<ImageBean> = ArrayList(),

    @SerializedName("isLibraryBookmark")
    val isLibraryBookmark: Boolean = false,

    var getViewType: Int = 0,

    val libraryDetailList: MutableList<LibraryDetailBean> = ArrayList(),
    val title: String? = null,
    val value: String? = null,
    var isValueShow: Boolean = false,
) : Serializable {
    fun getFileUrl(): String {
        return if (images.isNotEmpty()) {
            images[0].imageUrl.orEmpty()
        } else {
            ""
        }
    }

    fun getPinching(): String? {
        return if (isPinching) MyApplication.instance?.getString(R.string.yes).toString() else MyApplication.instance?.getString(R.string.no)
    }

    //Get sun details from sun or otherSun
    fun getSunDetails(context: Context): String {
        return if (sun?.title?.trim()?.lowercase().equals(context.getString(R.string.other).lowercase()).not()) sun?.title ?: otherSun.orEmpty()
        else if (otherSun?.trim()?.isNotEmpty() == true) return otherSun.trim()
        else ""
    }
}