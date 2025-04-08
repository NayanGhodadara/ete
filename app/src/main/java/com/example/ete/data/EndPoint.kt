package com.example.ete.data

import com.example.ete.BuildConfig
import com.example.ete.data.Constant.ApiObject.USER_ID
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
        const val SOCIAL_LOGIN: String = "${V1}social-login"
        const val LOGOUT: String = "${V1}logout"
    }

    //User end point
    object User {
        const val USERS: String = "${V1}users"
        const val BLOCK_USERS: String = "${USERS}/blocked-users"
        const val BLOCK_UNBLOCK: String = "${USERS}/block-unblock/{$USER_ID}"
        const val USER_POST: String = "${USERS}/post"
        const val REPORT_USER = "$USERS/report"
        const val OTHER_USER: String = "${USERS}/another-user/{$USER_ID}"
        const val OTHER_USER_POST: String = "${USERS}/another-user-post/{$USER_ID}"
    }

    //DropDownList end point
    object DropDown {
        const val COUNTRY_LIST: String = "${V1}country-list"
        const val DROP_DOWN_LIST: String = "${V1}seeder-dropdown/list"
    }

    object AWS {
        const val AWS_TOKEN: String = "${V1}aws/token"
    }

    //App version end point
    object AppVersion {
        const val CHECK_APP_VERSION: String = "${V1}check-app-version"
    }
}