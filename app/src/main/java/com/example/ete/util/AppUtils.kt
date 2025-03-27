package com.example.ete.util

import java.text.DecimalFormat
import kotlin.math.floor
import kotlin.math.log10
import kotlin.math.pow

object AppUtils {

    //Get count in format
    fun getCounterString(count: Long): String {
        val suffix = charArrayOf(' ', 'k', 'M', 'B', 'T', 'P', 'E')
        val value = floor(log10(count.toDouble())).toInt()
        val base = value / 3

        return if (value >= 3 && base < suffix.size) {
            DecimalFormat("#0.0").format(count / 10.0.pow((base * 3).toDouble())) + suffix[base]
        } else {
            DecimalFormat("#,##0").format(count)
        }
    }
}