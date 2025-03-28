package com.example.ete.data

import com.example.ete.BuildConfig
import com.example.ete.data.EndPoint.URLs.V1

object EndPoint {

    //URL
    object URLs {
        const val BASE_URL: String = BuildConfig.BASE_URL
        const val V1 = "v1/"
    }

    //Auth end point
    object Auth {
        const val SEND_OTP: String = "${V1}send-otp"
        const val VERIFY_OTP: String = "${V1}verify-otp"
    }

    //User end point
    object User {
        const val USERS: String = "${V1}users"
    }

    //DropDownList end point
    object DropDown {
        const val COUNTRY_LIST: String = "${V1}country-list"
        const val DROP_DOWN_LIST: String = "${V1}seeder-dropdown/list"
    }

    //App version end point
    object AppVersion {
        const val CHECK_APP_VERSION: String = "${V1}check-app-version"
    }
}